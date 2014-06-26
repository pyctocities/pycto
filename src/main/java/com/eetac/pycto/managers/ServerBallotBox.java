package com.eetac.pycto.managers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.eetac.pycto.models.Ballot_Box;
import com.eetac.pycto.models.Ballot_Box_photo;
import com.eetac.pycto.models.CA_CR;
import com.eetac.pycto.models.CA_CR_keys;
import com.google.gson.Gson;

/*
 * Funciones de la Urna;
 * - Votar
 * - Voto No repetido
 *  
 * Funciones auxiliares:
 * 
 * 
 * */

public class ServerBallotBox {
	private static SessionFactory sessionFactory;
	private AnnotationConfiguration config;

	public ServerBallotBox() {
		super();

		config = new AnnotationConfiguration();
		config.addAnnotatedClass(Ballot_Box.class);
		config.addAnnotatedClass(CA_CR.class);
		config.configure();
  
		sessionFactory = config.buildSessionFactory();
	}
	
	public List<String> listimages()
	{
		final File folder = new File("C:\\xampp\\htdocs\\images\\");
		List<String> llistaimatges = new ArrayList<String>();
		
	    for (final File fileEntry : folder.listFiles()) {
	    	
	    	llistaimatges.add(fileEntry.getName());
	    	
	    }
	    
	    return llistaimatges;

	}
	

	public boolean vote_not_repited(String pseudo) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion
				.createQuery("from Ballot_Box where pseudonimum= :pseudo");
		q.setParameter("pseudo", pseudo);

		Ballot_Box PhotoExtracted = (Ballot_Box) q.uniqueResult();
		sesion.getTransaction().commit();
		sesion.close();

		if (PhotoExtracted == null) {
			return false;
		} else {
			return true;
		}

	}

	public boolean vote(String ballet_vote) throws IOException {

		// ballet_vote hace referencia al voto entero, es decir con:
		// 1º: Votos en claro
		// 2º: E(H(votos), clave privada usuario)
		// 3º: pseudonimo en claro
		// 4º: clave publica usuario en claro
		// 5º: Certificado:E(H(pseudonimo_usuario, clave publica usuario), clave
		// privada CA)

		// suponemos que todo esto lo separararemos por el patrón "$$&&$$"

		if (verificate_certificate(ballet_vote) == true) {
			System.out
					.println("OK: la clave publica y el pseudonimo están certificados por la CA");

			if (verificate_inpunity_votes(ballet_vote) == true) {
				System.out.println("OK: los votos no se han modificado");

				insert_vote_foto_into_database(ballet_vote);
				return true;

			} else {
				System.out.println("ERROR: no hay impunidad en los votos");
				return false;
			}

		} else {
			System.out
					.println("ERROR: no está certificado por la CA o se han modificado los datos");
			return false;
		}
	}

	public void insert_vote_foto_into_database(String vote) throws IOException {

		String[] vote_parts = vote.split("-");

		String[] JSON_Strings = vote_parts[0].split("&");
		

		FileWriter fichero = new FileWriter("..//votes_tot.txt",true);

		PrintWriter pw = new PrintWriter(fichero);
		try {
			for (int i = 0; i < 5; i++) {
				String linea = null;
				Ballot_Box vote_to_insert = null;
				String pseudonimo_kpub_cliente = vote_parts[2];
				BigInteger pseudonim_cliente = new BigInteger(
						pseudonimo_kpub_cliente);
				Ballot_Box_photo foto=new Ballot_Box_photo(JSON_Strings[i]);
				String cert = vote_parts[4];
				BigInteger cert_cliente = new BigInteger(cert);

				vote_to_insert = new Ballot_Box(pseudonim_cliente,
						cert_cliente, foto.getId());
				System.out.println("foto_" + i + ": "
						+ vote_to_insert.getPhoto_voted());
				System.out.println(""+cert_cliente);
				System.out.println(""+pseudonim_cliente);
				linea =":"+vote_to_insert.getPhoto_voted()+":"+vote_to_insert.getPseudonimum()+":"+vote_to_insert.getPseudonimum_certificated()+":..";
				pw.println(linea);
				
			}
			fichero.close();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
	           try {
	               if (null != fichero)
	                  fichero.close();
	               } catch (Exception e2) {
	                  e2.printStackTrace();
	               }
	            }

	}

	public boolean verificate_certificate(String vote) {
		CA_CR_keys cacr_keys = new CA_CR_keys();
		// Este es el patrón de división nuestro mensaje
		String[] vote_parts = vote.split("-");

		// clave publica de la CA. SUPONEMOS (segun JUAN) que ya la tenemos por
		// defecto.
		// RSAPublicKey pubKey = CA_CR_keys.getPubKey();
		// BigInteger e = pubKey.getPublicExponent();
		// BigInteger n = pubKey.getModulus();

		// Cogeremos i los fijamos:

		BigInteger n = new BigInteger(
				"142481047092856236353738743136977805095584310438694847447732929440125014292455197492587396319295661312523380616370560540364318696807371547116571684603696850643510934945905963494827054536380775599044293414232308287850735160312467680401874891460335471177568255416928550974265562615081919894910514184398490834123");
		BigInteger e = new BigInteger("65537");

		// Ahora veremos si el Usuario tiene un certificado firmado por la CA
		// vote_parts[4] = el certificado firmado por la CA
		BigInteger certificate = new BigInteger(vote_parts[4]);

		// desencriptamos el certificado
		System.out.println("Certificat abans aplicar clau pub: " + certificate);

		BigInteger decrypted_certificated = certificate.modPow(e, n);

		System.out.println("Pseudonim cert: " + decrypted_certificated);

		// luego entonces, si todo es correcto, decrypted_certificated tiene que
		// ser igual al hash del pseudonimo y la clavepublica del usuario
		// (posición 3 y 4 del mensaje)

		String pseudonimo_kpub_cliente = vote_parts[2];
		BigInteger pseudonim_cliente = new BigInteger(pseudonimo_kpub_cliente);
		System.out.println("Pseudonim urls: " + pseudonimo_kpub_cliente);

		// Hacemos el HASH de los datos en claro
		// String hash_pseudonimo_kpub_cliente = null;
		// MessageDigest md = null;
		// BigInteger pseudonim_cegado2 = null;
		//
		// try {
		// md = MessageDigest.getInstance("SHA-1");
		// md.update(pseudonimo_kpub_cliente.getBytes());
		// byte[] passbytes = md.digest();
		// pseudonim_cegado2 = new BigInteger(1,passbytes);
		//
		// } catch (NoSuchAlgorithmException e1) {
		// e1.printStackTrace();
		// }
		// Comparamos nuetro hash con el hash del certificado por la CA
		if (pseudonim_cliente.toString().equals(
				decrypted_certificated.toString())) {
			return true;
			// es lo mismo
		} else {
			return false;
			// no es lo mismo
		}

	}

	public boolean verificate_inpunity_votes(String vote) {
		// todo esto está mejor explicado en el JUNIT test de
		// "test_ballot_box_vote_inpunity_code.java"

		// partimos el codigo por el patrón de división "..//.."
		String[] vote_parts = vote.split("-");

		// se hace el hash de los votos en claro
		String clear_vote_post_hash = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(vote_parts[0].getBytes());
			byte[] passbytes = md.digest();
			BigInteger HASH_json_id_fotos = new BigInteger(1, passbytes);
			clear_vote_post_hash = HASH_json_id_fotos.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// se desencripta con la clave publica del cliente (verificada
		// anteriormente) los votos
		// hasheados la clave publica del usuario la tenemos en claro e la 4
		// posicion del mensaje de voto
		// en el la 4º pos del mensaje está separado el exponente publico del
		// cliente y el modulo por ",,"
		String[] kpub_parts = vote_parts[3].split("\\.\\.");
		BigInteger client_pub_key = new BigInteger(kpub_parts[0]);
		BigInteger client_modulus = new BigInteger(kpub_parts[1]);

		// Cogemos el hash encriptado con la clave privada del usuario, i
		// aplicamos su clave publica
		// para obtener el hash

		BigInteger hash_votos_encriptado_usuario = new BigInteger(vote_parts[1]);
		BigInteger hash_votos_desencriptado = hash_votos_encriptado_usuario
				.modPow(client_pub_key, client_modulus);

		if (clear_vote_post_hash.equals(hash_votos_desencriptado.toString())) {
			return true;
			// es lo mismo
		} else {
			return false;
			// no es lo mismo
		}

	}

}

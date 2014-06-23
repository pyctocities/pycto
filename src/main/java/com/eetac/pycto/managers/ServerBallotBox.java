package com.eetac.pycto.managers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

	public boolean vote(String ballet_vote) {

		// ballet_vote hace referencia al voto entero, es decir con:
		// 1º: Votos en claro
		// 2º: E(H(votos), clave privada usuario)
		// 3º: pseudonimo en claro
		// 4º: clave publica usuario en claro
		// 5º: Certificado:E(H(pseudonimo_usuario, clave publica usuario), clave
		// privada CA)

		// suponemos que todo esto lo separararemos por el patrón "$$&&$$"

		if (verificate_certificate(ballet_vote) == true) {
			System.out.println("la clave publica y el pseudonimo están certificados por la CA");
			
			if (verificate_inpunity_votes(ballet_vote) == true) {
				System.out.println("los votos no se han modificado");
				
				insert_vote_foto_into_database(ballet_vote);
				return true;
				
			} else {
				System.out.println("ERROR: no hay impunidad en los votos");
				return false;
			}
			
		} else {
			System.out.println("ERROR: no está certificado por la CA o se han modificado los datos");
			return false;
		}
	}

	 public void insert_vote_foto_into_database(String vote) {
	 Session sesion = sessionFactory.openSession();
	 sesion.beginTransaction();
	
	 //volvemos a subdivir nuestro mensaje del cliente
	 String[] vote_parts = vote.split("-");
	

	 // obtenemos el objeto y lo vamos guardando en la base de datos
	 String[] JSON_Strings = vote_parts[0].split("&");
	 
		for (int i = 0; i < 5; i++) {
			Ballot_Box vote_to_insert = null;
			Ballot_Box_photo foto=null;
			foto.setId(JSON_Strings[i]);
			vote_to_insert = new Ballot_Box(vote_parts[2], vote_parts[4],
					foto.getId());

			// lo insertamos en la base de datos
			sesion.save(vote_to_insert);
		}
	 sesion.getTransaction().commit();
	 
	 }

	public boolean verificate_certificate(String vote) {
		CA_CR_keys cacr_keys = new CA_CR_keys();
		//Este es el patrón de división nuestro mensaje
		String[] vote_parts = vote.split("-");

		//clave publica de la CA. SUPONEMOS (segun JUAN) que ya la tenemos por defecto.
//		RSAPublicKey pubKey = CA_CR_keys.getPubKey();
//		BigInteger e = pubKey.getPublicExponent();
//		BigInteger n = pubKey.getModulus();
		
		//Cogeremos i los fijamos:
		
		BigInteger n = new BigInteger("142481047092856236353738743136977805095584310438694847447732929440125014292455197492587396319295661312523380616370560540364318696807371547116571684603696850643510934945905963494827054536380775599044293414232308287850735160312467680401874891460335471177568255416928550974265562615081919894910514184398490834123");
		BigInteger e = new BigInteger("65537");

		//Ahora veremos si el Usuario tiene un certificado firmado por la CA
		//vote_parts[4] = el certificado firmado por la CA
		BigInteger certificate = new BigInteger(vote_parts[4]);

		// desencriptamos el certificado
		System.out.println("Certificat abans aplicar clau pub: "+certificate);

		BigInteger decrypted_certificated = certificate.modPow(e, n);
		
		System.out.println("Pseudonim cert: "+decrypted_certificated);

		// luego entonces, si todo es correcto, decrypted_certificated tiene que
		// ser igual al hash del pseudonimo y la clavepublica del usuario 
		// (posición 3 y 4 del mensaje)

		String pseudonimo_kpub_cliente = vote_parts[2];
		BigInteger pseudonim_cliente = new BigInteger(pseudonimo_kpub_cliente);
		System.out.println("Pseudonim urls: "+pseudonimo_kpub_cliente);
		
		// Hacemos el HASH de los datos en claro
//		String hash_pseudonimo_kpub_cliente = null;
//		MessageDigest md = null;
//		BigInteger pseudonim_cegado2 = null;
//		
//		try {
//			md = MessageDigest.getInstance("SHA-1");
//			md.update(pseudonimo_kpub_cliente.getBytes());
//			byte[] passbytes = md.digest();
//			pseudonim_cegado2 = new BigInteger(1,passbytes);
//			
//		} catch (NoSuchAlgorithmException e1) {
//			e1.printStackTrace();
//		}
		// Comparamos nuetro hash con el hash del certificado por la CA
		if (pseudonim_cliente.toString().equals(decrypted_certificated.toString())) {
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
			BigInteger HASH_json_id_fotos = new BigInteger(1,passbytes);
			clear_vote_post_hash = HASH_json_id_fotos.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// se desencripta con la clave publica del cliente (verificada anteriormente) los votos 
		// hasheados la clave publica del usuario la tenemos en claro e la 4 posicion del mensaje de voto
		// en el la 4º pos del mensaje está separado el exponente publico del cliente y el modulo por ",,"
		String[] kpub_parts = vote_parts[3].split(",,");
		BigInteger client_pub_key = null;
		client_pub_key.add(new BigInteger(kpub_parts[0]));

		BigInteger client_modulus = null;
		client_modulus.add(new BigInteger(kpub_parts[1]));

		// pasamos a BigInteger el encriptado del hash de los votos
		BigInteger encrypted_vote_hash = null;
		encrypted_vote_hash.add(new BigInteger(vote_parts[4]));

		if (clear_vote_post_hash.equals(encrypted_vote_hash.modPow(
				client_pub_key, client_modulus))) {
			return true;
			// es lo mismo
		} else {
			return false;
			// no es lo mismo
		}

	}

}

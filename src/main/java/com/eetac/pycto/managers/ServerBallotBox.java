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

		// suponemos que todo esto lo separararemos por el patrón "..//.."

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
	 String[] vote_parts = vote.split("..//..");
	
	 //le quitamos al array de JSON lo necesario para obtener el JSON PURO
	 String replace = vote_parts[0].replace("[", "");
	 String replace_2 = replace.replace("]", "");
	 String JSON_String = replace_2;
	
	 // obtenemos el objeto y lo vamos guardando en la base de datos
	 String[] JSON_Strings = JSON_String.split(",");
	 
		for (int i = 0; i < 5; i++) {
			Gson g = new Gson();
			Ballot_Box vote_to_insert = null;
			Ballot_Box_photo foto = new Gson().fromJson(JSON_Strings[i],
					Ballot_Box_photo.class);

			// hacemos el formato ACORDADO para el voto: id_foto, pseudonimo,
			// certificado
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
		String[] vote_parts = vote.split("..//..");

		//clave publica de la CA. SUPONEMOS (segun JUAN) que ya la tenemos por defecto.
		RSAPublicKey pubKey = CA_CR_keys.getPubKey();
		BigInteger e = pubKey.getPublicExponent();
		BigInteger n = pubKey.getModulus();

		//Ahora veremos si el Usuario tiene un certificado firmado por la CA
		//vote_parts[4] = el certificado firmado por la CA
		BigInteger certificate = null;
		certificate.add(new BigInteger(vote_parts[4]));

		// desencriptamos el certificado
		BigInteger decrypted_certificated = certificate.modPow(e, n);

		// luego entonces, si todo es correcto, decrypted_certificated tiene que
		// ser igual al hash del pseudonimo y la clavepublica del usuario 
		//(posición 3 y 4 del mensaje)
		String pseudonimo_kpub_cliente = vote_parts[2] + vote_parts[3];

		//Hacemos el HASH de los datos en claro
		String hash_pseudonimo_kpub_cliente = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(pseudonimo_kpub_cliente.getBytes());
			byte[] passbytes = md.digest();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < passbytes.length; i++) {
				sb.append(Integer.toString((passbytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			hash_pseudonimo_kpub_cliente = sb.toString();

		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		// Comparamos nuetro hash con el hash del certificado por la CA
		if (hash_pseudonimo_kpub_cliente.equals(decrypted_certificated)) {
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
		String[] vote_parts = vote.split("..//..");

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

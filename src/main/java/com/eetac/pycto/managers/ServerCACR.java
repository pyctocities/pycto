package com.eetac.pycto.managers;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.eetac.pycto.models.CA_CR;
import com.eetac.pycto.models.CA_CR_keys;

/*
 * Funciones de la CA;
 * - Login
 * - Register
 *  
 * Funciones auxiliares:
 * - find user (dni)
 * - find user (CACR)
 * - obtain id unique
 * 
 * */

public class ServerCACR {
	private static SessionFactory sessionFactory;
	private AnnotationConfiguration config;

	public ServerCACR() {
		super();

		config = new AnnotationConfiguration();
		config.addAnnotatedClass(CA_CR.class);
		config.configure();

		sessionFactory = config.buildSessionFactory();
	}

	public boolean login(String mail, String password) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		Query q = sesion
				.createQuery("from CA_CR where mail= :mail and password= :password");
		q.setParameter("mail", mail);
		q.setParameter("password", String.valueOf(md.digest(password.getBytes())));

		CA_CR userExtracted = (CA_CR) q.uniqueResult();

		sesion.getTransaction().commit();
		sesion.close();

		if (userExtracted == null) {
			return false;
		} else {
			return true;
		}

	}

	public boolean find_user(CA_CR user) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion.createQuery("from CA_CR where mail = :mail");
		q.setParameter("mail", user.getMail());
		CA_CR userExtracted = (CA_CR) q.uniqueResult();

		sesion.getTransaction().commit();
		sesion.close();

		if (userExtracted == null) {
			return false;
		} else {
			return true;
		}
	}

	public long obtain_id_unique() {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion.createQuery("select MAX(id) from CA_CR");

		long id = (Long) q.uniqueResult();

		sesion.getTransaction().commit();
		sesion.close();

		return id + 1;

	}

	public boolean register(CA_CR user) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		ServerCACR cacr = new ServerCACR();
		CA_CR userRegister = new CA_CR(user.getDni(), user.getMail(),
				String.valueOf(md.digest(user.getPassword().getBytes())));

		boolean found = cacr.find_user(userRegister);

		if (found == false) {
			sesion.save(userRegister);
			sesion.getTransaction().commit();
			System.out
					.println("USER MANAGER_REGISTER: This user properly inserted.");
			return true;
		} else {
			System.out
					.println("USER MANAGER_REGISTER: This user already exist.");
			return false;
		}

	}

	public BigInteger certificate(BigInteger BlindedHashFromClient) {
		CA_CR_keys ca_cr_keys = new CA_CR_keys();

		RSAPrivateKey privKey = ca_cr_keys.getPrivKey();
		BigInteger d = privKey.getPrivateExponent();
		BigInteger n = privKey.getModulus();
		BigInteger certificado = BlindedHashFromClient.modPow(d, n);

		return certificado;

	}

	// public boolean find_user_dni(String dni) {
	// Session sesion = sessionFactory.openSession();
	// sesion.beginTransaction();
	//
	// Query q = sesion.createQuery("from CA_CR where dni = '"
	// +dni + "'");
	//
	// CA_CR userExtracted = (CA_CR) q.uniqueResult();
	//
	// sesion.getTransaction().commit();
	// sesion.close();
	//
	// if (userExtracted == null) {
	// return false;
	// } else {
	// return true;
	// }
	// }
}

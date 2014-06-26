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
		String generatedPassword = null;
		
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(password.getBytes());
			byte[] passbytes = md.digest();
			
			StringBuilder sb = new StringBuilder();
            for(int i=0; i< passbytes.length ;i++)
            {
                sb.append(Integer.toString((passbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		Query q = sesion
				.createQuery("from CA_CR where mail= :mail and password= :password");
		q.setParameter("mail", mail);
		
		
		System.out.println("LOGIN: El digestivo: "+md.digest(password.getBytes()));
		System.out.println("LOGIN: String value of: "+String.valueOf(md.digest(password.getBytes())));

		q.setParameter("password", generatedPassword);

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
	
	public String obtain_dni(String user) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion.createQuery("from CA_CR where mail = :mail");
		q.setParameter("mail", user);
		CA_CR userExtracted = (CA_CR) q.uniqueResult();

		sesion.getTransaction().commit();
		sesion.close();

		return userExtracted.getDni();
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
		String generatedPassword = null;
		
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(user.getPassword().getBytes());
			byte[] passbytes = md.digest();
			
			StringBuilder sb = new StringBuilder();
            for(int i=0; i< passbytes.length ;i++)
            {
                sb.append(Integer.toString((passbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		ServerCACR cacr = new ServerCACR();
		CA_CR userRegister = new CA_CR(user.getDni(), user.getMail(),
				generatedPassword);
		
		System.out.println("REGISTER: El digestivo: "+md.digest(user.getPassword().getBytes()));
		System.out.println("REGISTER: String value of: "+String.valueOf(md.digest(user.getPassword().getBytes())));

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

//		Exponente privado (d): 25325775456416059273669268398550504633274782246790480624866230743053254804103968397336355983389195526707413546047250838969381881448629773951232254576568310279439540864110785500521787384059249839336895732023564768440664836324251769958295229225415811198874191889445669390421153457915379339792764070528724732673
//		Modulo (n): 116770461945063970495178193544097679903681539616428009618112998748239845229812985567485068389149972367653282789313260041757167606901565322600387524144122531512560719010928041933023332981891055964777391834955581376608807380723402615024634833763126757710281554371271787428870643553674812237978516449325529381923
//		Exponente publico (e): 25325775456416059273669268398550504633274782246790480624866230743053254804103968397336355983389195526707413546047250838969381881448629773951232254576568310279439540864110785500521787384059249839336895732023564768440664836324251769958295229225415811198874191889445669390421153457915379339792764070528724732673
	
		RSAPrivateKey privKey = ca_cr_keys.getPrivKey();
		
//		
//		BigInteger d = privKey.getPrivateExponent();
//		BigInteger n = privKey.getModulus();
		
		BigInteger d = new BigInteger("19107770006242480756107386872009672841068259219153898015138390784583651229326773132159705605235051456059447216645266896398400857931244770551101645421393890630604203416323778586973289315927416337473761274890808293202804141849439283076686056076902005168981376906723788853667321204526140965238908058142676087113");
		BigInteger n = new BigInteger("142481047092856236353738743136977805095584310438694847447732929440125014292455197492587396319295661312523380616370560540364318696807371547116571684603696850643510934945905963494827054536380775599044293414232308287850735160312467680401874891460335471177568255416928550974265562615081919894910514184398490834123");

		System.out.println("Exponente privado (d): "+ca_cr_keys.getPrivKey().getPrivateExponent());
		System.out.println("Modulo priv (n): "+ca_cr_keys.getPrivKey().getModulus());
		System.out.println("Modulo pub (n): "+ca_cr_keys.getPubKey().getModulus());
		System.out.println("Exponente public (e): "+ca_cr_keys.getPubKey().getPublicExponent());

		
		System.out.println("Abans de firmar amb PRIVADA: "+BlindedHashFromClient);
		BigInteger certificado = BlindedHashFromClient.modPow(d, n);

		return certificado;

	}

}

package com.eetac.pycto.managers;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.eetac.pycto.models.CA_CR;

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

	public boolean login(String dni, String password) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion.createQuery("from CA_CR where dni='"
				+ dni + "' and password='"+password+"'");

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

	Query q = sesion.createQuery("from CA_CR where dni = '"
			+ user.getDni() + "'");

	CA_CR userExtracted = (CA_CR) q.uniqueResult();

	sesion.getTransaction().commit();
	sesion.close();

	if (userExtracted == null) {
		return false;
	} else {
		return true;
	}
}

	public long obtain_id_unique(){
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();
		
		Query q = sesion.createQuery("select MAX(id) from CA_CR");

		long id = (Long) q.uniqueResult();

		sesion.getTransaction().commit();
		sesion.close();
		
		return id+1;

	}
	
	public boolean register(CA_CR user) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();
		
		ServerCACR cacr = new ServerCACR();
		CA_CR userRegister= new CA_CR(user.getDni(), user.getMail(), user.getPassword());
		
		boolean found = cacr.find_user(userRegister);

		if (found == false) {
			sesion.save(userRegister);
			sesion.getTransaction().commit();
			System.out.println("USER MANAGER_REGISTER: This user properly inserted.");
			return true;
		} else {
			System.out
				.println("USER MANAGER_REGISTER: This user already exist.");
			return false;
		}

	}
	
//	public boolean find_user_dni(String dni) {
//	Session sesion = sessionFactory.openSession();
//	sesion.beginTransaction();
//
//	Query q = sesion.createQuery("from CA_CR where dni = '"
//			+dni + "'");
//
//	CA_CR userExtracted = (CA_CR) q.uniqueResult();
//
//	sesion.getTransaction().commit();
//	sesion.close();
//
//	if (userExtracted == null) {
//		return false;
//	} else {
//		return true;
//	}
//}
}

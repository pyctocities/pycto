package com.eetac.pycto.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.eetac.pycto.models.User;

public class UserManager {
	private static SessionFactory sessionFactory;
	private AnnotationConfiguration config;
	
	public UserManager() {
		super();
		
		config = new AnnotationConfiguration();
		config.addAnnotatedClass(User.class);
		config.configure();

		sessionFactory = config.buildSessionFactory();
	}

	public boolean login(String user, String password) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion.createQuery("from User where user='"
				+ user + "' and password='"+password+"'");

		User userExtracted = (User) q.uniqueResult();

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
		
		Query q = sesion.createQuery("select MAX(id) from User");

		long id = (Long) q.uniqueResult();

		sesion.getTransaction().commit();
		sesion.close();
		
		return id;

	}
	
	public boolean find_user(User user) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion.createQuery("from User where user = '"
				+ user.getUser() + "'");

		User userExtracted = (User) q.uniqueResult();

		sesion.getTransaction().commit();
		sesion.close();

		if (userExtracted == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean register(String name, String password, String mail) {

		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();
		UserManager user_manager = new UserManager();
		User userRegister= new User(user_manager.obtain_id_unique()+1, name, password, mail);
		
		boolean found = find_user(userRegister);

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

}

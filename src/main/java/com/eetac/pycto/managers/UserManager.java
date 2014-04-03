package com.eetac.pycto.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.eetac.pycto.models.User;

public class UserManager {
	private static SessionFactory sessionFactory;

	
	public UserManager() {
		// TODO Auto-generated constructor stub
		
		sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
	}
	
	
	public boolean login(String user, String password){
		Session session = sessionFactory.openSession();
	    Transaction tx = null;
        tx = session.beginTransaction();

        /*
        
        Codigo con hibernate
        
        */
            
        
        session.close(); 

		return false;
	}
	
	
	public void register(User userRegister){
		
		Session session = sessionFactory.openSession();
	    Transaction tx = null;
        tx = session.beginTransaction();
        
        /*
        
        Codigo con hibernate
        
        */
        
		session.save(userRegister);
        tx.commit();
        
        session.close(); 
		
	}
	
}

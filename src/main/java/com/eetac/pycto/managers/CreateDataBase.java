package com.eetac.pycto.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.eetac.pycto.models.Photo;
import com.eetac.pycto.models.User;


public class CreateDataBase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(User.class);
		config.addAnnotatedClass(Photo.class);
		config.configure();
		
	
		System.out.println("CREATING DATABASE PROCESS INIT.");
		//con esta se crean las tablas
		new SchemaExport (config).create(true, true);
		
		System.out.println("Tables creation completed.");
		
		
		System.out.println("Init data insertion.");
		
		SessionFactory factory = config.buildSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		
		User user;
		
		user= new  User(1, "Juan Francisco", "1234", "jotagarcia1992@gmail.com", 4);
		sesion.save(user);
		
		user= new  User(2, "Victor Manuel", "1234", "victorramirez1991@gmail.com",2);
		sesion.save(user);
		
		user= new  User(3, "Anna", "1234", "anna_hurtado_@mail_inventado.com",2);
		sesion.save(user);
		
		user= new  User(4, "Jordi", "1234", "jordi_pala_@mail_inventado.com",1);
		sesion.save(user);
		
		
		Photo photo;
		photo=new Photo(1, "foto_a", 1);
		sesion.save(photo);
		
		photo=new Photo(2, "foto_a", 3);
		sesion.save(photo);
		
		photo=new Photo(3, "foto_a", 2);
		sesion.save(photo);
		
		photo=new Photo(4, "foto_b", 2);
		sesion.save(photo);
		
		photo=new Photo(5, "foto_c", 3);
		sesion.save(photo);
		
		photo=new Photo(6,  "foto_b",1);
		sesion.save(photo);
		
		photo=new Photo(7,  "foto_d",2);
		sesion.save(photo);
		
		photo=new Photo(8,  "foto_d",1);
		sesion.save(photo);
		
		photo=new Photo(9,  "foto_e",1);
		sesion.save(photo);
		
		sesion.getTransaction().commit();
		
		
		System.out.println("Inital data inserted.");
		System.out.println("CREATING DATABASE PROCESS FINISHED.");
	}
}

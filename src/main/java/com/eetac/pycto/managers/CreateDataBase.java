package com.eetac.pycto.managers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.eetac.pycto.models.Ballot_Box;
import com.eetac.pycto.models.CA_CR;


public class CreateDataBase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				AnnotationConfiguration config = new AnnotationConfiguration();
				config.addAnnotatedClass(CA_CR.class);
				config.addAnnotatedClass(Ballot_Box.class);
				config.configure();
				
			
				System.out.println("CREATING DATABASE PROCESS INIT.");
				
				new SchemaExport (config).create(true, true);
				
				System.out.println("Tables creation completed.");
				
				
				System.out.println("Init data insertion.");
				
				SessionFactory factory = config.buildSessionFactory();
				Session sesion = factory.getCurrentSession();
				
				sesion.beginTransaction();
				

				CA_CR user;
				
				user= new CA_CR("47667698Z", "jotagarcia1992@gmail.com", "1234");
				sesion.save(user);
				user= new CA_CR("46123123Z", "victorramirez1991@gmail.com", "1234");
				sesion.save(user);
				user= new CA_CR("47123123Z", "jordi_pala@carteras", "1234");
				sesion.save(user);
				
				

				Ballot_Box votes;
				
				votes= new Ballot_Box("pseudo_jf", "pseudo_jf_cert", "JSON_VOTOS");
				sesion.save(votes);
				votes= new Ballot_Box("pseudo_victor", "pseudo_victor_cert", "JSON_VOTOS");
				sesion.save(votes);
				
				sesion.getTransaction().commit();
				
				
				
				System.out.println("Inital data inserted into ballot box data bsae.");
				System.out.println("CREATING ballot box DATABASE PROCESS FINISHED.");
		
	}
}

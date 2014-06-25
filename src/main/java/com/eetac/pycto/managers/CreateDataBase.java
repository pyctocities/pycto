package com.eetac.pycto.managers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.eetac.pycto.models.Ballot_Box;
import com.eetac.pycto.models.Ballot_Box_photo;
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
				config.addAnnotatedClass(Ballot_Box_photo.class);
				config.configure();
				
			
				System.out.println("CREATING DATABASE PROCESS INIT.");
				
				new SchemaExport (config).create(true, true);
				
				System.out.println("Tables creation completed.");
				
				
				System.out.println("Init data insertion.");
				
				SessionFactory factory = config.buildSessionFactory();
				Session sesion = factory.getCurrentSession();
				
				sesion.beginTransaction();
				
				String password = "1234";
				MessageDigest md = null;
				String hashedpassword = null;
				
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
		            hashedpassword = sb.toString();
		            
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				

				CA_CR user;
				
				user= new CA_CR("47667698Z", "jotagarcia1992@gmail.com", hashedpassword);
				sesion.save(user);
				user= new CA_CR("46123123Z", "victorramirez1991@gmail.com", hashedpassword);
				sesion.save(user);
				user= new CA_CR("47123123Z", "jordipalasole@gmail.com", hashedpassword);
				sesion.save(user);
				
				

//				Ballot_Box votes;
//				
//				votes= new Ballot_Box("pseudo_jf", "pseudo_jf_cert", "JSON_VOTOS");
//				sesion.save(votes);
//				votes= new Ballot_Box("pseudo_victor", "pseudo_victor_cert", "JSON_VOTOS");
//				sesion.save(votes);
//				
				Ballot_Box_photo photos;
				photos= new Ballot_Box_photo("12");
				sesion.save(photos);
		
				sesion.getTransaction().commit();
				
				
				
				System.out.println("Inital data inserted into ballot box data bsae.");
				System.out.println("CREATING ballot box DATABASE PROCESS FINISHED.");
		
	}
}

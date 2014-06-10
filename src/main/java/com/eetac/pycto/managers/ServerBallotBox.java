package com.eetac.pycto.managers;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.eetac.pycto.models.Ballot_Box;
import com.eetac.pycto.models.CA_CR;

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


		Query q = sesion.createQuery("from Ballot_Box where pseudonimum= :pseudo");
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

	public String vote(Ballot_Box user, String photos) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();
		String output_msn = null;

		ServerBallotBox box = new ServerBallotBox();

	
			if (box.vote_not_repited(user.getPseudonimum()) == true) {
				output_msn = "nok";
			} else {

				Calendar c = new GregorianCalendar();
				int dia = c.get(Calendar.DATE);
				int mes = c.get(Calendar.MONTH);
				int annio = c.get(Calendar.YEAR);

				if (annio >= 2014) {
					if ((mes + 1) >= 4) {
						if ((dia + 1) >= 5) {
							sesion.save(user);
							sesion.getTransaction().commit();
							output_msn = "ok";
						} else {
							output_msn = "nok";
						}
					} else {
						output_msn = "nok";
					}
				} else {
					output_msn = "nok";
				}

			}
		

		return output_msn;

	}
}

package com.eetac.pycto.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.eetac.pycto.models.Photo;
import com.eetac.pycto.models.User;

public class PhotoVotes {
	private static SessionFactory sessionFactory;
	private AnnotationConfiguration config;

	public PhotoVotes() {
		super();

		config = new AnnotationConfiguration();
		config.addAnnotatedClass(User.class);
		config.addAnnotatedClass(Photo.class);
		config.configure();

		sessionFactory = config.buildSessionFactory();
	}

	public long obtain_max_photo_vote() {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion.createQuery("select MAX(num_sec) from Photo ");

		long id = (Long) q.uniqueResult();

		sesion.getTransaction().commit();
		sesion.close();

		return id;

	}

	public int obtain_num_votes_of_user(User user) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion.createQuery("from Photo where id_user ='"
				+ user.getId() + "'");

		List<Photo> num_tot_votes = (List<Photo>) q.list();

		sesion.getTransaction().commit();
		sesion.close();

		return num_tot_votes.size();
	}

	public boolean vote_not_repited(User user, String photo_id) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();

		Query q = sesion.createQuery("from Photo where id_photo='" + photo_id
				+ "' and id_user='" + user.getId() + "'");

		Photo PhotoExtracted = (Photo) q.uniqueResult();
		sesion.getTransaction().commit();
		sesion.close();

		if (PhotoExtracted == null) {
			return false;
		} else {
			return true;
		}

	}

	public String vote(User user, String photo_id) {
		Session sesion = sessionFactory.openSession();
		sesion.beginTransaction();
		String output_msn = null;

		PhotoVotes photo = new PhotoVotes();

		if (photo.obtain_num_votes_of_user(user) == 5) {
			output_msn = "nok, to many votes";
		} else {
			if (photo.vote_not_repited(user, photo_id) == true) {
				output_msn = "nok, vote repited";
			} else {
				   
				 Calendar c = new GregorianCalendar(); 
				int dia = c.get(Calendar.DATE);
				int mes = c.get(Calendar.MONTH);
				int annio = c.get(Calendar.YEAR);
				
				if (annio >= 2014) {
					if ((mes+1) >= 4) {
						if ((dia+1) >= 5) {
							PhotoVotes photo_votes = new PhotoVotes();
							Photo photo_voted = new Photo(
									photo_votes.obtain_max_photo_vote() + 1,
									photo_id, user.getId());

							sesion.save(photo_voted);
							sesion.getTransaction().commit();
							output_msn = "ok";
						} else {
							output_msn = "Out of Date by Day "+ dia;
						}
					} else {
						output_msn = "Out of Date by Month"+ mes;
					}
				} else {
					output_msn = "Out of Date by year"+ annio;
				}

			}
		}

		return output_msn;

	}

}

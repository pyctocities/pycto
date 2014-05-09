package com.eetac.pycto.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Photo {

	@Id
	long num_sec;
	String id_photo;
	long id_user;
	
	public Photo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getNum_sec() {
		return num_sec;
	}

	public void setNum_sec(long num_sec) {
		this.num_sec = num_sec;
	}

	public String getId_photo() {
		return id_photo;
	}

	public void setId_photo(String id_photo) {
		this.id_photo = id_photo;
	}

	public long getId_user() {
		return id_user;
	}

	public void setId_user(long id_user) {
		this.id_user = id_user;
	}

	public Photo(long num_sec, String id_photo, long id_user) {
		super();
		this.num_sec = num_sec;
		this.id_photo = id_photo;
		this.id_user = id_user;
	}
	
	



}

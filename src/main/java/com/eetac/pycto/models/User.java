package com.eetac.pycto.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	long id;
	String user;
	String password;
	String email;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(long id, String user, String password, String email) {
		super();
		this.id = id;
		this.user = user;
		this.password = password;
		this.email = email;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}

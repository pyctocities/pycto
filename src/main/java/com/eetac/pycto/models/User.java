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
	int num_votes;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(long id, String user, String password, String email, int num_votes) {
		super();
		this.id = id;
		this.user = user;
		this.password = password;
		this.email = email;
		this.num_votes=num_votes;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getNum_votes() {
		return num_votes;
	}
	public void setNum_votes(int num_votes) {
		this.num_votes = num_votes;
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

package com.eetac.pycto.models;

import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class Ballot_Box_photo {
	
	@Id
	String id;
	
	
	public Ballot_Box_photo(){
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Ballot_Box_photo(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}

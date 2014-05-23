package com.eetac.pycto.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ballot_Box {
	
	@Id
	String pseudonimum;
	String pseudonimum_certificated;
	String photo_voted;
	
	public Ballot_Box() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ballot_Box(String pseudonimum, String pseudonimum_certificated,
			String photo_voted) {
		super();
		this.pseudonimum = pseudonimum;
		this.pseudonimum_certificated = pseudonimum_certificated;
		this.photo_voted = photo_voted;
	}

	public String getPseudonimum() {
		return pseudonimum;
	}

	public void setPseudonimum(String pseudonimum) {
		this.pseudonimum = pseudonimum;
	}

	public String getPseudonimum_certificated() {
		return pseudonimum_certificated;
	}

	public void setPseudonimum_certificated(String pseudonimum_certificated) {
		this.pseudonimum_certificated = pseudonimum_certificated;
	}

	public String getPhoto_voted() {
		return photo_voted;
	}

	public void setPhoto_voted(String photo_voted) {
		this.photo_voted = photo_voted;
	}
	


}

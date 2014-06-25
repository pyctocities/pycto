package com.eetac.pycto.models;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ballot_Box {
	
	@Id
	BigInteger pseudonimum;
	BigInteger pseudonimum_certificated;
	String photo_voted;
	
	public Ballot_Box() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ballot_Box(BigInteger pseudonimum, BigInteger pseudonimum_certificated,
			String photo_voted) {
		super();
		this.pseudonimum = pseudonimum;
		this.pseudonimum_certificated = pseudonimum_certificated;
		this.photo_voted = photo_voted;
	}

	public BigInteger getPseudonimum() {
		return pseudonimum;
	}

	public void setPseudonimum(BigInteger pseudonimum) {
		this.pseudonimum = pseudonimum;
	}

	public BigInteger getPseudonimum_certificated() {
		return pseudonimum_certificated;
	}

	public void setPseudonimum_certificated(BigInteger pseudonimum_certificated) {
		this.pseudonimum_certificated = pseudonimum_certificated;
	}

	public String getPhoto_voted() {
		return photo_voted;
	}

	public void setPhoto_voted(String photo_voted) {
		this.photo_voted = photo_voted;
	}
	


}

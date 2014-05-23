package com.eetac.pycto.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CA_CR {

	@Id
	String dni;
	String mail;
	String password;

	public CA_CR() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CA_CR(String dni, String mail, String password) {
		super();
		this.dni = dni;
		this.mail = mail;
		this.password = password;
	}


	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

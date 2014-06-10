package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.ServerCACR;
import com.eetac.pycto.models.CA_CR;

public class test_ca_cr_register {

	@Test
	public void test() {
		ServerCACR cacr=new ServerCACR();
		CA_CR user=new CA_CR("1421", "4@", "1234");
		assertEquals("prueba register", true, cacr.register(user));
	}

}

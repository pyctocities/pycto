package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.ServerCACR;

public class test_ca_cr_login {

	@Test
	public void test() {
		ServerCACR cacr=new ServerCACR();
		assertEquals("prueba login", true, cacr.login("@", "1234"));
	}

}

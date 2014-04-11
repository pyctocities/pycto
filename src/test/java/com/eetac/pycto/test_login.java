package com.eetac.pycto;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.eetac.pycto.managers.UserManager;


public class test_login {

	@Test
	public void test() {
		UserManager test = new UserManager();
		assertEquals("prueba login", true, test.login("Juan Francisco", "1234"));
		
	}
	


}

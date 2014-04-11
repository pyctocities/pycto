package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.UserManager;
import com.eetac.pycto.models.User;

public class test_find {

	@Test
	public void test() {
		UserManager test = new UserManager();
		User user= new User( 1, "Juan Francisco", "1234", "jotagarcia1992@gmail.com");
				
		assertEquals("prueba find", true, test.find_user(user));
	}

}

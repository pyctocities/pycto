package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.UserManager;
import com.eetac.pycto.models.User;

public class test_register {

	@Test
	public void test() {
		UserManager test = new UserManager();				
		assertEquals("prueba register", true, test.register("victoria fernanda", "1234", "victoriafernanda@hotma.co"));
	}

}

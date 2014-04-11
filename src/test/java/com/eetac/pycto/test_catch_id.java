package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.UserManager;
import com.eetac.pycto.models.User;

public class test_catch_id {

	@Test
	public void test() {
		UserManager test = new UserManager();
		assertEquals("prueba obtain_unique_id", 6, test.obtain_id_unique());
	}

}

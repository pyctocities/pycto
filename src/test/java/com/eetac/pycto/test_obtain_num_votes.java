package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.UserManager;

public class test_obtain_num_votes {

	@Test
	public void test() {
		UserManager test = new UserManager();
		assertEquals("prueba num_votes", 4, test.obtain_num_votes("Juan Francisco"));
	}

}

package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.managers.ServerCACR;

public class test_ca_cr_obtain_id_unique {

	@Test
	public void test() {
		ServerCACR cacr=new ServerCACR();
		assertEquals("prueba obtain_unique_id", 4, cacr.obtain_id_unique());
	}

}

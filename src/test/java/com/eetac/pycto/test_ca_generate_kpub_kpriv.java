package com.eetac.pycto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eetac.pycto.models.CA_CR_keys;

public class test_ca_generate_kpub_kpriv {

	@Test
	public void test() {
		CA_CR_keys keys= new CA_CR_keys();
		System.out.println("clave privada CACR ---->"+keys.getPrivKey());
		System.out.println("clave pública CACR ---->"+keys.getPubKey());
	}

}

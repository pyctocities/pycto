package com.eetac.pycto;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

import com.eetac.pycto.managers.ServerCACR;
import com.eetac.pycto.models.CA_CR_keys;

public class test_ca_certificate {

	@Test
	public void test() {
		ServerCACR serverCACR = new ServerCACR();
		Random rand = new Random();
		BigInteger cegado_cliente = new BigInteger(4, rand);
		System.out.println("certificado  ---->"
				+ serverCACR.certificate(cegado_cliente));
	}

}

package com.eetac.pycto;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.util.Random;

import org.junit.Test;

import com.eetac.pycto.managers.ServerCACR;
import com.eetac.pycto.models.CA_CR_keys;

public class test_ballot_box_verificate_certificate {

	@Test
	public void test() {		
		ServerCACR serverCACR = new ServerCACR();
		CA_CR_keys cacr_keys = new CA_CR_keys();
		Random rand = new Random();
		BigInteger cegado_cliente = new BigInteger(4, rand);
		System.out.println("antes de encriptar con privKey de la CA ------> "
				+ cegado_cliente);

		BigInteger certificado = serverCACR.certificate(cegado_cliente);
		System.out.println("certificado----------------------------------->"
				+ certificado);

		RSAPublicKey pubKey = CA_CR_keys.getPubKey();
		BigInteger e = pubKey.getPublicExponent();
		BigInteger n = pubKey.getModulus();
		System.out.println("despues de desencriptar con la pública ------->"
				+ certificado.modPow(e, n));
	}

}

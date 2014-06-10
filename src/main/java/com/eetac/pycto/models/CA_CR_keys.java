package com.eetac.pycto.models;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class CA_CR_keys {

	static RSAPublicKey pubKey = null;
	static RSAPrivateKey privKey = null;
	static int i = 0;

	public CA_CR_keys() {
		super();
		// TODO Auto-generated constructor stub
		if(i == 0) {
			KeyPairGenerator keyGen = null;
			try {
				keyGen = KeyPairGenerator.getInstance("RSA");
				keyGen.initialize(1024, new SecureRandom());
				KeyPair keypair = keyGen.genKeyPair();
				privKey = (RSAPrivateKey) keypair.getPrivate();
				pubKey = (RSAPublicKey) keypair.getPublic();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			privKey=privKey;
			pubKey=pubKey;
		}
		
	}

	public static RSAPublicKey getPubKey() {
		return pubKey;
	}

	public static RSAPrivateKey getPrivKey() {
		return privKey;
	}

}

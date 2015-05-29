package com.tyn.jasca.testapp.group2;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class V23EncryptionStrength {
	
	public KeyPair getKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(256);
		
		return keyGen.generateKeyPair();
	}
}

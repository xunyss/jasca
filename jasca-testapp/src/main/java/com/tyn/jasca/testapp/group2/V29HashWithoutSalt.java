package com.tyn.jasca.testapp.group2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class V29HashWithoutSalt {

	public byte[] getHash(String value) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		byte[] result = digest.digest(value.getBytes());
		
		return result;
	}
}

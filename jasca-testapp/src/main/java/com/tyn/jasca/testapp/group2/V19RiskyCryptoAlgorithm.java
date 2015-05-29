package com.tyn.jasca.testapp.group2;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class V19RiskyCryptoAlgorithm {
	
	public String enc(String msg)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, InvalidKeyException {
		Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
		SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec("12345678".getBytes()));
		c.init(Cipher.ENCRYPT_MODE, key);
		return new String(c.update(msg.getBytes()));
	}
	
	public String getHash(String value) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.reset();
		digest.update("salt".getBytes());
		return new String(digest.digest(value.getBytes()));
	}
}

package com.tyn.jasca.testapp.group2;

import javax.crypto.spec.SecretKeySpec;

import com.tyn.jasca.testapp.common.Printer;

public class V25 {
	
	public boolean verifyAdmin(String password) {
		SecretKeySpec key = new SecretKeySpec("dddddd".getBytes(), "AES");
		Printer.print(key);
		return false;
	}
	
	public boolean ferifyAdmin(String password) {
		if (password.equals("68af404b513073584c4b6f22b6c63e6b")) {
			Printer.print("Entering Diagnostic Mode...");
			return true;
		}
		Printer.print("Incorrect Password!");
		return false;
	}
}

package com.tyn.jasca.testapp.group4;


public class V35EmptyCatchBlock {
	
	public int toNumber(String str) {
		int result = 0;
		try {
			result = Integer.parseInt(str);
		}
		catch (NumberFormatException nfe) {
			
		}
		return result;
	}
}

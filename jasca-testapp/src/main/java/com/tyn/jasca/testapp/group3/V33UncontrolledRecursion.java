package com.tyn.jasca.testapp.group3;

public class V33UncontrolledRecursion {
	
	public int factorial(int n) {
		return n * factorial(n - 1);
	}
}

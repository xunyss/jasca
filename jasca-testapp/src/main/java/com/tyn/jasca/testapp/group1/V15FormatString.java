package com.tyn.jasca.testapp.group1;

import java.io.PrintWriter;


public class V15FormatString {
	
	public void printFormat(String format, Object[] data) {
		PrintWriter writer = new PrintWriter(System.out);
		writer.printf(format, data);
	}
}

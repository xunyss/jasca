package com.tyn.jasca.testapp.group6;

import javax.servlet.http.HttpServlet;

import com.tyn.jasca.testapp.common.Printer;

public class V42DebugCode extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5239704616365726295L;
	
	private static String addS(String s) {
		return s + "S";
	}
	
	public static void main(String[] args) {
		Printer.print(addS("abc"));
	}
}

package com.tyn.jasca.testapp.group1;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class V13InputsSecurityDecision extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4732603911204778313L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean hasRole = false;
		Cookie[] cookies = request.getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			Cookie c = cookies[i];
			if (c.getName().equals("admin")) {
				hasRole = true;
			}
		}
		if (hasRole) {
			doSomething();
		}
	}
	
	private boolean doSomething() {
		File file = new File("D:/hello");
		return file.delete();
	}
}

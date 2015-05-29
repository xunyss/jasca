package com.tyn.jasca.testapp.group6;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tyn.jasca.testapp.common.Printer;

public class V41WrongSessionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5823179591244265180L;
	
	private String name = null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		name = request.getParameter("name");
		doWrite();
	}
	
	private void doWrite() throws IOException {
		Printer.print(name);
	}
}

package com.tyn.jasca.testapp.group1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class V06OpenRedirect extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5950663474147572995L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getParameter("link");
		response.sendRedirect(url);
	}
}

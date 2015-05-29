package com.tyn.jasca.testapp.group1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class V11HTTPResponseSplitting extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9092418556004766609L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String author = request.getParameter("author");
		response.setContentType("text/html");
	//	Cookie cookie = new Cookie("author", author);
	//	response.addCookie(cookie);
		response.setHeader("author", author);
		
		request.getRequestDispatcher("abc.jsp").forward(request, response);
	}
}

package com.tyn.jasca.testapp.group2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class V27InfoExposureByCookies extends HttpServlet {

	private static final long serialVersionUID = 5403674073249591330L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String maxAge = request.getParameter("maxAge");
		HttpSession session = request.getSession();
		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		cookie.setMaxAge(Integer.parseInt(maxAge));
		response.addCookie(cookie);
	}
}

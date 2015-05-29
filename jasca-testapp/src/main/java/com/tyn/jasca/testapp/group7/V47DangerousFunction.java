package com.tyn.jasca.testapp.group7;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class V47DangerousFunction extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3788283048683359247L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSessionContext sessionCtx = 
				request.getSession().getSessionContext();
		HttpSession session = sessionCtx.getSession("JSESSIONID");
		session.invalidate(); 
	}
}

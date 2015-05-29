package com.tyn.jasca.testapp.group7;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class V46ReverseDNSResolution extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3045060564184401887L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean trusted = false;
		String ip = request.getRemoteAddr();
		InetAddress addr = InetAddress.getByName(ip);
		if (addr.getCanonicalHostName().endsWith("trustme.com")) {
			trusted = true;
		}
		if (trusted) {
			resp.getWriter().write("that's ok");
		}
	}
}

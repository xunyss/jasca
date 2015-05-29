package com.tyn.jasca.testapp.group6;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.tyn.jasca.testapp.common.Printer;

public class V41WrongSessionFilter implements Filter {
	String name = null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		name = request.getParameter("name");
		doWrite();
	}
	private void doWrite() throws IOException {
		Printer.print(name);
	}
}

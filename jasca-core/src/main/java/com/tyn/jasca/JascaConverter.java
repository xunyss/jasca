package com.tyn.jasca;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;

/**
 * 
 * @author S.J.H.
 */
public class JascaConverter implements ViolationConverter {
	
	private String input = null;
//	private String output = null;
	
	@Override
	public void setInput(String input) {
		this.input = input + (input.endsWith("/") ? "" : "/");
	}
	
	@Override
	public void setOutput(String output) {
//		this.output = output;
	}
	
	@Override
	public Violation convert(Violation violation) {
		if (AnalyzerEngine.FINDBUGS.equals(violation.getAnalyzer())) {
			return convertFindBugs(violation);
		}
		else if (AnalyzerEngine.PMD.equals(violation.getAnalyzer())) {
			return convertPMD(violation);
		}
		else {
			return violation;
		}
	}
	
	private Violation convertFindBugs(Violation violation) {
		
		/*
		 * severity
		 */
		
		String type = violation.getType();
		if ("SQL_NONCONSTANT_STRING_PASSED_TO_EXECUTE".equals(type)) {
			violation.setType("01.SQL삽입");
		}
		else if ("PATH_TRAVERSAL_IN".equals(type)) {
			violation.setType("02.경로조작및자원삽입");
		}
		else if ("XSS_REQUEST_PARAMETER_TO_SERVLET_WRITER".equals(type)) {
			violation.setType("03.크로스사이트스크립트");
		}
		else if ("COMMAND_INJECTION".equals(type)) {
			violation.setType("04.운영체제명령어삽입");
		}
		else if ("UNVALIDATED_REDIRECT".equals(type)) {
			violation.setType("06.신뢰되지않는URL주소로자동접속연결");
		}
		else if ("XQUERY_INJECTION".equals(type)) {
			violation.setType("07.XQuery삽입");
		}
		else if ("XPATH_INJECTION".equals(type)) {
			violation.setType("08.XPath삽입");
		}
		else if ("LDAP_INJECTION".equals(type)) {
			violation.setType("09.LDAP삽입");
		}
		return violation;
	}
	
	private Violation convertPMD(Violation violation) {
		
		/*
		 * filename
		 */
		String filepath = violation.getFilename();
		filepath = filepath.replace('\\', '/').substring(input.length());
		if (filepath.endsWith(".java")) {
			filepath = filepath.substring("/src".length());
		}
		violation.setFilename(filepath);
		
		/*
		 * severity
		 */
		
		String type = violation.getType();
		if ("DynamicClassLoading".equals(type)) {
			violation.setType("30.무결성검사없는코드다운로드");
		}
		else if ("ReverseDNSResolution".equals(type)) {
			violation.setType("46.DNSlookup에의존한보안결정");
		}
		else if ("UseDangerousMethod".equals(type)) {
			violation.setType("47.취약한API사용");
		}
		return violation;
	}
}

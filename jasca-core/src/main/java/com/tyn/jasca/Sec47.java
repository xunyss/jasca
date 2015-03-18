package com.tyn.jasca;

/**
 * 
 * @author S.J.H.
 */
public class Sec47 {
	
	public static Violation conv(Violation v) {
		if ("F".equals(v.getEngine())){
			return convFindBugs(v);
		}
		else {
			return convPMD(v);
		}
	}
	
	private static Violation convFindBugs(Violation v) {
		String type = v.getType();
		if ("SQL_NONCONSTANT_STRING_PASSED_TO_EXECUTE".equals(type)) {
			v.setType("01.SQL삽입");
		}
		else if ("PATH_TRAVERSAL_IN".equals(type)) {
			v.setType("02.경로조작및자원삽입");
		}
		else if ("XSS_REQUEST_PARAMETER_TO_SERVLET_WRITER".equals(type)) {
			v.setType("03.크로스사이트스크립트");
		}
		else if ("COMMAND_INJECTION".equals(type)) {
			v.setType("04.운영체제명령어삽입");
		}
		else if ("UNVALIDATED_REDIRECT".equals(type)) {
			v.setType("06.신뢰되지않는URL주소로자동접속연결");
		}
		else if ("XQUERY_INJECTION".equals(type)) {
			v.setType("07.XQuery삽입");
		}
		else if ("XPATH_INJECTION".equals(type)) {
			v.setType("08.XPath삽입");
		}
		else if ("LDAP_INJECTION".equals(type)) {
			v.setType("09.LDAP삽입");
		}
		return v;
	}
	
	private static Violation convPMD(Violation v) {
		String type = v.getType();
		if ("DynamicClassLoading".equals(type)) {
			v.setType("30.무결성검사없는코드다운로드");
		}
		else if ("ReverseDNSResolution".equals(type)) {
			v.setType("46.DNSlookup에의존한보안결정");
		}
		else if ("UseDangerousMethod".equals(type)) {
			v.setType("47.취약한API사용");
		}
		return v;
	}
}

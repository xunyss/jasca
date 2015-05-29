package com.tyn.jasca.testapp.group1;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.tyn.jasca.testapp.common.Log;
import com.tyn.jasca.testapp.common.Printer;

public class V09LDAPInjection {

	public void queryUsingLDAP(String name) {
		try {
			InitialDirContext context = getContext();
			NamingEnumeration<SearchResult> answers =
					context.search("dc=People,dc=example,dc=com", "(uid=" + name + ")", new SearchControls());
			
			Printer.print(answers);
		}
		catch (NamingException ne) {
			Log.log("¿¡·¯");
		}
	}
	
	private InitialDirContext getContext() {
		return null;
	}
}

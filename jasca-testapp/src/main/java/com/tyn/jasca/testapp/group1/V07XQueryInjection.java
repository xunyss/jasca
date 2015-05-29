package com.tyn.jasca.testapp.group1;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import com.tyn.jasca.testapp.common.Log;
import com.tyn.jasca.testapp.common.Printer;

public class V07XQueryInjection {

	public void queryUsingXQuery(String name) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=rootDir");
		
		try {
			DirContext ctx = new InitialDirContext(env);
			XQDataSource xqds = (XQDataSource) ctx.lookup("xqj/personnel");
			XQConnection conn = xqds.getConnection();
			
			String es = "doc('user.xml')/userlist/user[uname='" + name + "']";
			XQPreparedExpression expr = conn.prepareExpression(es);
			XQResultSequence result = expr.executeQuery();
			
			while (result.next()) {
				String str = result.getAtomicValue();
				if (str.indexOf('>') < 0) {
					Printer.print(str);
				}
			}
		}
		catch (NamingException ne) {
			Log.log("에러: NamingException");
		}
		catch (XQException e) {
			Log.log("에러: XQException");
		}
	}
}

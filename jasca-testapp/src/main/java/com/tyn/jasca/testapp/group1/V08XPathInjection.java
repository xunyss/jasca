package com.tyn.jasca.testapp.group1;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

import com.tyn.jasca.testapp.common.Log;

public class V08XPathInjection {
	
	public Object queryUsingXPath(String name, String passwd) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		
		try {
			String xpathQuery = "//users/user[login/text()='" + name + "' and password/text() = '" + passwd + "']home_dir/text()";
			XPathExpression expr = xpath.compile(xpathQuery);
			Object result = expr.evaluate(getSource(), XPathConstants.NODESET);
			return result;
		}
		catch (XPathExpressionException xee) {
			Log.log("에러");
			return null;
		}
	}
	
	private InputSource getSource() {
		return null;
	}
}

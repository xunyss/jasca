package com.tyn.jasca.common;

import java.util.Properties;

import org.junit.Test;

public class UtilsTest {
	
	@Test
	public void propertyConverterTest() throws Exception {
		Properties p = new Properties();
		
		p.setProperty("a", "aval");
		p.setProperty("b", "bval");
		
		String s = Utils.serializeProperties(p);
		System.out.println(s);
		
		
		System.out.println(Utils.deserializeProperties(s));
	}
}

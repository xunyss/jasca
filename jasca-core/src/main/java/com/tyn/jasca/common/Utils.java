package com.tyn.jasca.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author S.J.H.
 */
public class Utils {
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getSlashedClassName(Class<?> clazz) {
		return getSlashedClassName(clazz.getName());
	}
	
	/**
	 * 
	 * @param className
	 * @return
	 */
	public static String getSlashedClassName(String className) {
		if (className.indexOf('.') >= 0) {
			return className.replace('.', '/');
		}
		return className;
	}
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	public static String serializeProperties(Properties properties) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			properties.store(bos, null);
			return bos.toString();
		}
		catch (IOException ioe) {
			return null;
		}
		finally {
			IOUtils.closeQuietly(bos);
		}
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	public static Properties deserializeProperties(String string) {
		ByteArrayInputStream bis = new ByteArrayInputStream(string.getBytes());
		try {
			Properties properties = new Properties();
			properties.load(bis);
			return properties;
		}
		catch (IOException ioe) {
			return null;
		}
		finally {
			IOUtils.closeQuietly(bis);
		}
	}
}

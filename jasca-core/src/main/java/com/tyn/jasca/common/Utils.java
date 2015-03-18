package com.tyn.jasca.common;

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
}

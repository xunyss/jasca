package com.tyn.jasca.common;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * @author S.J.H.
 */
public class DomUtils {
	
	/**
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static String getElText(Element element, String tagName) {
		NodeList elementList = element.getElementsByTagName(tagName);
		if (elementList != null && elementList.getLength() > 0) {
			return elementList.item(0).getTextContent();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static List<String> getElTextList(Element element, String tagName) {
		List<String> list = new ArrayList<String>();
		
		NodeList elementList = element.getElementsByTagName(tagName);
		if (elementList != null) {
			int elementLen = elementList.getLength();
			for (int idx = 0; idx < elementLen; idx++) {
				list.add(elementList.item(idx).getTextContent());
			}
		}
		
		return list;
	}
}

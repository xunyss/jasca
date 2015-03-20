package com.tyn.jasca;

/**
 * 
 * @author S.J.H.
 */
public enum Severity {
	HIGH	(1, "High"),
	MEDIUM	(2, "Medium"),
	LOW		(3, "Low");
	
	private int value;
	private String text;
	
	private Severity(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}
}

package com.tyn.jasca;

import com.tyn.jasca.rules.Pattern;
import com.tyn.jasca.rules.Rule;

/**
 * 
 * @author S.J.H.
 */
public class Violation {
	
	private Rule rule;
	private Pattern pattern;
	
	private String filename;
	private int beginline;
	private int endline;
	private String message;
	
	public Rule getRule() {
		return rule;
	}
	
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public int getBeginline() {
		return beginline;
	}
	
	public void setBeginline(int beginline) {
		this.beginline = beginline;
	}
	
	public int getEndline() {
		return endline;
	}
	
	public void setEndline(int endline) {
		this.endline = endline;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}

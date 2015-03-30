package com.tyn.jasca;



/**
 * 
 * @author S.J.H.
 */
public class Violation {
	
	private RulePattern rulePattern;
	
	private String filename;
	private int beginline;
	private int endline;
	private String message;
	
	public RulePattern getRulePattern() {
		return rulePattern;
	}
	
	public void setRulePattern(RulePattern rulePattern) {
		this.rulePattern = rulePattern;
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

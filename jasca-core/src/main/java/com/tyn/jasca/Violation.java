package com.tyn.jasca;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;



/**
 * 
 * @author S.J.H.
 */
public class Violation {
	
	private AnalyzerEngine analyzer;
	private String filename;
	private int beginline;
	private int endline;
	private String message;
	private Severity severity;
	private String type;
	
	public AnalyzerEngine getAnalyzer() {
		return analyzer;
	}
	
	public void setAnalyzer(AnalyzerEngine analyzer) {
		this.analyzer = analyzer;
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
	
	public Severity getSeverity() {
		return severity;
	}
	
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}

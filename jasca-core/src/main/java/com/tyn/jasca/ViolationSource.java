package com.tyn.jasca;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;

/**
 * 
 * @author S.J.H.
 */
public class ViolationSource {
	
	private AnalyzerEngine analyzer;
	private String type;
	
	public ViolationSource(AnalyzerEngine analyzer, String type) {
		this.analyzer = analyzer;
		this.type = type;
	}
	
	public AnalyzerEngine getAnalyzer() {
		return analyzer;
	}
	
	public void setAnalyzer(AnalyzerEngine analyzer) {
		this.analyzer = analyzer;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof ViolationSource) {
			ViolationSource vsrc = (ViolationSource) object;
			return
					vsrc.analyzer == analyzer &&
					vsrc.type.equals(type);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() & analyzer.ordinal();
	}
}

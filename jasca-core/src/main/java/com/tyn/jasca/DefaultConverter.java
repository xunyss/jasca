package com.tyn.jasca;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;

/**
 * 
 * @author S.J.H.
 */
public class DefaultConverter implements ViolationConverter {
	
	private String input = null;
//	private String output = null;
	
	@Override
	public void setInput(String input) {
		this.input = input + (input.endsWith("/") ? "" : "/");
	}
	
	@Override
	public void setOutput(String output) {
//		this.output = output;
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.ViolationConverter#convert(com.tyn.jasca.Violation)
	 */
	@Override
	public Violation convert(Violation violation) {
		/*
		 * filename change
		 * TODO 실제 파일조사를 통한 정확한 변환
		 */
		if (AnalyzerEngine.PMD.equals(violation.getPattern().getAnalyzerEngine())) {
			String filepath = violation.getFilename();
			filepath = filepath.replace('\\', '/').substring(input.length());
			if (filepath.endsWith(".java")) {
				filepath = filepath.substring("/src".length());
			}
			violation.setFilename(filepath);
		}
		
		return violation;
	}
}

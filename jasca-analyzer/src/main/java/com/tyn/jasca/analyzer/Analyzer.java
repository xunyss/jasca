package com.tyn.jasca.analyzer;

/**
 * 
 * @author S.J.H.
 */
public abstract class Analyzer {
	
	public enum AnalyzerEngine {
		FINDBUGS,
		PMD;
	}
	
	public abstract void applyConfiguration(Configuration configuration);
	
	public abstract void execute();
}

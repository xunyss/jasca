package com.tyn.jasca.analyzer;

public abstract class Analyzer {
	
	public enum AnalyzerEngine {
		FINDBUGS,
		PMD;
	}
	
	public abstract void applyConfiguration(Configuration configuration);
	
	public abstract void execute();
}

package com.tyn.jasca.analyzer;

public abstract class Analyzer {
	
	protected void log(Object o) {
		System.out.println(">>>> " + o);
	}
	
	public abstract void applyConfiguration(Configuration configuration);
	
	public abstract void execute();
}

package com.tyn.jasca.analyzer.pmd;



/**
 * 
 * @author S.J.H.
 */
public interface PmdProgress {
	
	/**
	 * 
	 * @param totalCount
	 */
	void startAnalyze(int totalCount);
	
	/**
	 * 
	 * @param passCount
	 */
	void analyzeFile(int passCount);
	
	/**
	 * 
	 */
	void finishAnalyze();
}

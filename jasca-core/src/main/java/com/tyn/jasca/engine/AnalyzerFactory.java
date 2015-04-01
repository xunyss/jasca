package com.tyn.jasca.engine;

import com.tyn.jasca.JascaConfiguration;
import com.tyn.jasca.analyzer.Analyzer;

/**
 * 
 * @author S.J.H.
 */
public interface AnalyzerFactory {
	
	/**
	 * 
	 * @param jascaConfiguration
	 * @return
	 */
	Analyzer getAnalyzer(JascaConfiguration jascaConfiguration);
}

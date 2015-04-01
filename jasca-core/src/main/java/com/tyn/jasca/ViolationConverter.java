package com.tyn.jasca;

/**
 * 
 * @author S.J.H.
 */
public interface ViolationConverter {
	
	/**
	 * 
	 * @param input
	 */
	void setInput(String input);
	
	/**
	 * 
	 * @param output
	 */
	void setOutput(String output);
	
	/**
	 * 
	 * @param violation
	 * @return
	 */
	Violation convert(Violation violation);
}

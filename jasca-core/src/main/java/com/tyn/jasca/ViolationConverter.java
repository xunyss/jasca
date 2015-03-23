package com.tyn.jasca;

/**
 * 
 * @author S.J.H.
 */
public interface ViolationConverter {
	
	void setInput(String input);
	
	void setOutput(String output);
	
	Violation convert(Violation violation);
}

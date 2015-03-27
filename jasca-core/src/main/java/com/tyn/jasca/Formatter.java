package com.tyn.jasca;

import java.io.IOException;

/**
 * 
 * @author S.J.H.
 */
public interface Formatter {
	
	void setInput(String input);
	
	void setOutput(String output);
	
	void start();
	
	void writeDocumentHead() throws IOException;
	
	void writeViolationHead() throws IOException;
	
	void writeViolationBody(Violation violation) throws IOException;
	
	void writeViolationTail() throws IOException;
	
	void writeDocumentTail() throws IOException;
	
	void finish();
}

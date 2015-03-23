package com.tyn.jasca;

import java.io.IOException;
import java.io.Writer;

/**
 * 
 * @author S.J.H.
 */
public interface Formatter {
	
	void setInput(String input);
	
	void setOutput(String output);
	
	void setWriter(Writer writer);
	
	void start();
	
	void writeHead() throws IOException;
	
	void writeBody(Violation violation) throws IOException;
	
	void writeTail() throws IOException;
	
	void finish();
}

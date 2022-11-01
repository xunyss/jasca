package com.tyn.jasca.formatters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;

import com.tyn.jasca.Formatter;
import com.tyn.jasca.JascaException;
import com.tyn.jasca.Violation;
import com.tyn.jasca.common.Utils;

/**
 * 
 * @author S.J.H.
 * 
 * @ TODO TextFormatter 구현
 */
public class TextFormatter implements Formatter {

	/**
	 * 
	 */
//	private static final Logger log = LoggerFactory.getLogger(TextFormatter.class);
	
//	private String input = null;
	private String output = null;
	private Writer writer = null;
	
	@Override
	public void setInput(String input) {
//		this.input = input;
	}

	@Override
	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public void start() {
		try {
			writer = Utils.isEmpty(output) ?
					new OutputStreamWriter(System.out) :
						new BufferedWriter(new FileWriter(output));
		}
		catch (IOException ioe) {
			throw new JascaException(ioe);
		}
	}

	@Override
	public void writeDocumentHead() throws IOException {
		
	}

	@Override
	public void writeViolationHead() throws IOException {
		
	}

	@Override
	public void writeViolationBody(Violation violation) throws IOException {
		writer.write(violation.getMessage());
		writer.write(Utils.CRLF);
	}

	@Override
	public void writeViolationTail() throws IOException {
		
	}

	@Override
	public void writeDocumentTail() throws IOException {
		
	}

	@Override
	public void finish() {
		try {
			writer.flush();
		}
		catch (IOException e) {
			throw new IllegalStateException(e);
		}
		finally {
			if (!writer.getClass().equals(OutputStreamWriter.class)) {
				IOUtils.closeQuietly(writer);
			}
		}
	}
}

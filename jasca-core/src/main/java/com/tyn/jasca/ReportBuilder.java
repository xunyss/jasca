package com.tyn.jasca;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author S.J.H.
 */
public class ReportBuilder {
	
	/**
	 * 
	 * @param violations
	 * @param converter
	 * @param formatter
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void build(List<Violation> violations,
			ViolationConverter converter,
			Formatter formatter,
			String input,
			String output
			) throws IOException {
		
		converter.setInput(input);
		converter.setOutput(output);
		
		for (Violation violation : violations) {
			converter.convert(violation);
		}
		
		sort(violations);
		
		formatter.setInput(input);
		formatter.setOutput(output);
		formatter.setWriter(new BufferedWriter(new FileWriter(output)));
		
		formatter.start();
		formatter.writeHead();
		
		for (Violation violation : violations) {
			formatter.writeBody(violation);
		}
		
		formatter.writeTail();
		formatter.finish();
	}
	
	/**
	 * 
	 * @param violations
	 */
	private static void sort(List<Violation> violations) {
		Collections.sort(violations, new Comparator<Violation>() {
			@Override
			public int compare(Violation violation1, Violation violation2) {
				return violation1.getSeverity().getValue() - violation2.getSeverity().getValue();
			}
		});
	}
}

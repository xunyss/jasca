package com.tyn.jasca;

import java.io.IOException;
import java.util.Iterator;

/**
 * 
 * @author S.J.H.
 */
public class ReportBuilder {
	
	/**
	 * 
	 */
//	private static final Logger log = LoggerFactory.getLogger(ReportBuilder.class);
	
	/**
	 * 
	 * @param violations
	 * @param converter
	 * @param formatter
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void build(Results results,
			ViolationConverter converter,
			Formatter formatter,
			String input,
			String output
			) throws IOException {
		
		converter.setInput(input);
		converter.setOutput(output);
		
		/*
		 * 변환 결과가 null 이면 레포팅 항목에서 제거
		 */
		for (Iterator<Violation> vitr = results.iterator(); vitr.hasNext(); ) {
			Violation violation = vitr.next();
			if (converter.convert(violation) == null) {
				vitr.remove();
			}
		}
		
		results.sort();
		
		formatter.setInput(input);
		formatter.setOutput(output);
		
		formatter.start();
		formatter.writeDocumentHead();
		
		if (formatter instanceof SummaryFormatter) {
			((SummaryFormatter) formatter)
				.writeSummary(
						Summary.summary(results)
				);
		}
		
		formatter.writeViolationHead();
		for (Violation violation : results) {
			formatter.writeViolationBody(violation);
		}
		formatter.writeViolationTail();
		
		formatter.writeDocumentTail();
		formatter.finish();
	}
}

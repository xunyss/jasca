package com.tyn.jasca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tyn.jasca.ViolationSummary.TypeCounter;

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
	public static void build(ViolationResult violationResult,
			ViolationConverter converter,
			Formatter formatter,
			String input,
			String output
			) throws IOException {
		
		List<Violation> violations = violationResult.getViolations();
		
		converter.setInput(input);
		converter.setOutput(output);
		
		for (Violation violation : violations) {
			converter.convert(violation);
		}
		
		sort(violations);
		
		formatter.setInput(input);
		formatter.setOutput(output);
		
		formatter.start();
		formatter.writeDocumentHead();
		
		if (formatter instanceof SummaryFormatter) {
			((SummaryFormatter) formatter).writeSummary(summary(violations));
		}
		
		formatter.writeViolationHead();
		for (Violation violation : violations) {
			formatter.writeViolationBody(violation);
		}
		formatter.writeViolationTail();
		
		formatter.writeDocumentTail();
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
	
	/**
	 * 
	 * @param violations
	 * @return
	 */
	private static ViolationSummary summary(List<Violation> violations) {
		
		int total = 0;
		
		int[] severitySummary = new int[3];
		
		Map<ViolationSource, Integer> typeSummary = new HashMap<ViolationSource, Integer>();
		
		for (Violation violation : violations) {
			
			total++;
			
			severitySummary[violation.getSeverity().getValue() - 1]++;
			
			ViolationSource violationSource = new ViolationSource(violation.getAnalyzer(), violation.getType());
			if (typeSummary.get(violationSource) != null) {
				typeSummary.put(violationSource, typeSummary.get(violationSource).intValue() + 1);
			}
			else {
				typeSummary.put(violationSource, 1);
			}
		}
		
		List<TypeCounter> typeCounterList = new ArrayList<TypeCounter>();
		
		Set<ViolationSource> set = typeSummary.keySet();
		Iterator<ViolationSource> itr = set.iterator();
		while (itr.hasNext()) {
			ViolationSource violationSource = itr.next();
			typeCounterList.add(new TypeCounter(violationSource, typeSummary.get(violationSource)));
		}
		
		Collections.sort(typeCounterList, new Comparator<TypeCounter>() {
			@Override
			public int compare(TypeCounter typeCounter1, TypeCounter typeCounter2) {
				return typeCounter2.getTypeCount() - typeCounter1.getTypeCount();
			}
		});
		
		ViolationSummary violationSummary = new ViolationSummary();
		violationSummary.setViolationCount(total);
		violationSummary.setSeveritySummary(severitySummary);
		violationSummary.setTypeSummary(typeCounterList);
		
		return violationSummary;
	}
}

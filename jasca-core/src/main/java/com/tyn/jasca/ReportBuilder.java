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

import com.tyn.jasca.Summary.TypeCounter;

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
			((SummaryFormatter) formatter).writeSummary(summary(results));
		}
		
		formatter.writeViolationHead();
		for (Violation violation : results) {
			formatter.writeViolationBody(violation);
		}
		formatter.writeViolationTail();
		
		formatter.writeDocumentTail();
		formatter.finish();
	}
	
	/**
	 * 
	 * @param violations
	 * @return
	 */
	private static Summary summary(Results results) {
		
		int total = 0;
		
		int[] severitySummary = new int[3];
		
		Map<RulePattern, Integer> typeSummary = new HashMap<RulePattern, Integer>();
		
		for (Violation violation : results) {
			
			RulePattern rulePattern = violation.getRulePattern();
			
			/*
			 * 전체
			 */
			total++;
			
			/*
			 * 심각도별
			 */
			severitySummary[rulePattern.getSeverity().getValue() - 1]++;
			
			/*
			 * 룰패턴별
			 */
			if (typeSummary.get(rulePattern) != null) {
				typeSummary.put(rulePattern, typeSummary.get(rulePattern).intValue() + 1);
			}
			else {
				typeSummary.put(rulePattern, 1);
			}
		}
		
		List<TypeCounter> typeCounterList = new ArrayList<TypeCounter>();
		
		Set<RulePattern> set = typeSummary.keySet();
		Iterator<RulePattern> itr = set.iterator();
		while (itr.hasNext()) {
			RulePattern rulePattern = itr.next();
			typeCounterList.add(new TypeCounter(rulePattern, typeSummary.get(rulePattern)));
		}
		
		Collections.sort(typeCounterList, new Comparator<TypeCounter>() {
			@Override
			public int compare(TypeCounter typeCounter1, TypeCounter typeCounter2) {
				int countOrder = typeCounter2.getTypeCount() - typeCounter1.getTypeCount();
				return countOrder != 0
						? countOrder
						: typeCounter1.getRulePattern().compareTo(typeCounter2.getRulePattern());
			}
		});
		
		Summary summary = new Summary();
		summary.setViolationCount(total);
		summary.setSeveritySummary(severitySummary);
		summary.setTypeSummary(typeCounterList);
		
		return summary;
	}
}

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
				return
						violation1.getRulePattern().getSeverity().getValue()
						-
						violation2.getRulePattern().getSeverity().getValue();
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
		
		Map<RulePattern, Integer> typeSummary = new HashMap<RulePattern, Integer>();
		
		for (Violation violation : violations) {
			
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

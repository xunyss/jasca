package com.tyn.jasca;

import java.util.ArrayList;
import java.util.List;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;

/**
 * 
 * @author S.J.H.
 */
public class Summary {
	
	private int violationCount = 0;
	private int[] severitySummary = null;
	
	private List<RulePatternCounter> rulePatternSummary = null;
	private List<TypeCounter> typeSummary = null;
	
	public int getViolationCount() {
		return violationCount;
	}

	public void setViolationCount(int violationCount) {
		this.violationCount = violationCount;
	}

	public int[] getSeveritySummary() {
		return severitySummary;
	}

	public void setSeveritySummary(int[] severitySummary) {
		this.severitySummary = severitySummary;
	}

	public List<RulePatternCounter> getRulePatternSummary() {
		return rulePatternSummary;
	}

	public void setRulePatternSummary(List<RulePatternCounter> rulePatternSummary) {
		this.rulePatternSummary = rulePatternSummary;
	}
	
	public List<TypeCounter> getTypeSummary() {
		return typeSummary;
	}
	
	public void setTypeSummary(List<TypeCounter> typeSummary) {
		this.typeSummary = typeSummary;
	}
	
	public void buildTypeSummary() {
		if (rulePatternSummary == null) {
			return;
		}
		
		if (typeSummary != null) {
			typeSummary.clear();
		}
		
		typeSummary = new ArrayList<TypeCounter>();
		
		for (RulePatternCounter rulePatternCounter : rulePatternSummary) {
			RulePattern rulePattern = rulePatternCounter.getRulePattern();
			String typename = rulePattern.getTypename();
			
			int index = 0;
			
			if ((index = indexOfTypeCounter(typeSummary, typename)) > -1) {
				TypeCounter typeCounter = typeSummary.get(index);
				typeCounter.engines[rulePattern.getAnalyzerEngine().ordinal()] = true;
				typeCounter.count += rulePatternCounter.count;
			}
			else {
				TypeCounter typeCounter = new TypeCounter();
				typeCounter.typename = typename;
				typeCounter.engines[rulePattern.getAnalyzerEngine().ordinal()] = true;
				typeCounter.seveiry = rulePattern.getSeverity().getText();
				typeCounter.count = rulePatternCounter.count;
				
				typeSummary.add(typeCounter);
			}
		}
	}
	private int indexOfTypeCounter(List<TypeCounter> typeSummary, String typename) {
		if (typeSummary != null) {
			for (int index = 0; index < typeSummary.size(); index++) {
				TypeCounter typeCounter = typeSummary.get(index);
				if (typename.equals(typeCounter.getTypename())) {
					return index;
				}
			}
		}
		return -1;
	}


	/**
	 * 
	 * @author S.J.H.
	 */
	public static class RulePatternCounter {
		
		private RulePattern rulePattern = null;
		private int count = 0;
		
		public RulePatternCounter(RulePattern rulePattern, int typeCount) {
			this.rulePattern = rulePattern;
			this.count = typeCount;
		}

		public RulePattern getRulePattern() {
			return rulePattern;
		}

		public void setViolationSource(RulePattern rulePattern) {
			this.rulePattern = rulePattern;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int typeCount) {
			this.count = typeCount;
		}
	}
	
	
	/**
	 * 
	 * @author S.J.H.
	 */
	public static class TypeCounter {
		
		private String typename = null;
		
		private boolean[] engines = new boolean[AnalyzerEngine.values().length];
		private String seveiry = null;
		
		private int count = 0;
		
		public String getTypename() {
			return typename;
		}
		
		public boolean[] getEngines() {
			return engines;
		}

		public String getSeveiry() {
			return seveiry;
		}
		
		public int getCount() {
			return count;
		}
	}
}


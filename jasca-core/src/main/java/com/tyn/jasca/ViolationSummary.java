package com.tyn.jasca;

import java.util.List;

/**
 * 
 * @author S.J.H.
 */
public class ViolationSummary {
	
	private int violationCount = 0;
	private int[] severitySummary = null;
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

	public List<TypeCounter> getTypeSummary() {
		return typeSummary;
	}

	public void setTypeSummary(List<TypeCounter> typeSummary) {
		this.typeSummary = typeSummary;
	}
	
	
	public static class TypeCounter {
		
		private RulePattern rulePattern = null;
		private int typeCount = 0;
		
		public TypeCounter(RulePattern rulePattern, int typeCount) {
			this.rulePattern = rulePattern;
			this.typeCount = typeCount;
		}

		public RulePattern getRulePattern() {
			return rulePattern;
		}

		public void setViolationSource(RulePattern rulePattern) {
			this.rulePattern = rulePattern;
		}

		public int getTypeCount() {
			return typeCount;
		}

		public void setTypeCount(int typeCount) {
			this.typeCount = typeCount;
		}
	}
}


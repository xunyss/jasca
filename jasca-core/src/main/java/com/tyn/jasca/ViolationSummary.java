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
		
		private ViolationSource violationSource = null;
		private int typeCount = 0;
		
		public TypeCounter(ViolationSource violationSource, int typeCount) {
			this.violationSource = violationSource;
			this.typeCount = typeCount;
		}

		public ViolationSource getViolationSource() {
			return violationSource;
		}

		public void setViolationSource(ViolationSource violationSource) {
			this.violationSource = violationSource;
		}

		public int getTypeCount() {
			return typeCount;
		}

		public void setTypeCount(int typeCount) {
			this.typeCount = typeCount;
		}
	}
}


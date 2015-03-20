package com.tyn.jasca.analyzer.findbugs;

import edu.umd.cs.findbugs.FindBugs;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.config.AnalysisFeatureSetting;

/**
 * 
 * @author S.J.H.
 */
public class FindBugsConstant {
	
	/**
	 * 
	 */
	public enum EffortLevel {
		MIN		(FindBugs.MIN_EFFORT),
		LESS	(FindBugs.LESS_EFFORT),
		DEFAULT	(FindBugs.DEFAULT_EFFORT),
		MORE	(FindBugs.MORE_EFFORT),
		MAX		(FindBugs.MAX_EFFORT);
		
		private AnalysisFeatureSetting[] analysisFeatureSettings;
		
		private EffortLevel(AnalysisFeatureSetting[] analysisFeatureSettings) {
			this.analysisFeatureSettings = analysisFeatureSettings;
		}
		
		public AnalysisFeatureSetting[] getAnalysisFeatureSettings() {
			return analysisFeatureSettings;
		}
	}
	
	/**
	 * 
	 */
	public enum Priority {
		HIGH	(Priorities.HIGH_PRIORITY),
		MEDIUM	(Priorities.NORMAL_PRIORITY),
		LOW		(Priorities.LOW_PRIORITY),
		EXP		(Priorities.EXP_PRIORITY),
		IGNORE	(Priorities.IGNORE_PRIORITY);
		
		private int priorityThreshold;
		
		private Priority(int priorityThreshold) {
			this.priorityThreshold = priorityThreshold;
		}
		
		public int getPriorityThreshold() {
			return priorityThreshold;
		}
	}
	
	/**
	 * 
	 */
	public enum ReportFormat {
		PRINTING,
		SORTING,
		XML,
		EMACS,
		HTML,
		XDOCS,
		
		CUSTOM;
	}
}

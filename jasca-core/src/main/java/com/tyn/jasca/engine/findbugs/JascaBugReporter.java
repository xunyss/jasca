package com.tyn.jasca.engine.findbugs;

import com.tyn.jasca.RulePattern;
import com.tyn.jasca.RulePatternCollection;
import com.tyn.jasca.Severity;
import com.tyn.jasca.Violation;
import com.tyn.jasca.ViolationResult;
import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;

import edu.umd.cs.findbugs.AbstractBugReporter;
import edu.umd.cs.findbugs.AnalysisError;
import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.SourceLineAnnotation;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;

/**
 * 
 * @author S.J.H.
 */
public class JascaBugReporter extends AbstractBugReporter {
	
	/**
	 * 
	 */
//	private static final Logger log = LoggerFactory.getLogger(JascaBugReporter.class);
	
	/**
	 * 
	 * @see edu.umd.cs.findbugs.classfile.IClassObserver#observeClass(edu.umd.cs.findbugs.classfile.ClassDescriptor)
	 */
	@Override
	public void observeClass(ClassDescriptor classDescriptor) {
		
	}
	
	/**
	 * 
	 * @see edu.umd.cs.findbugs.BugReporter#finish()
	 */
	@Override
	public void finish() {
		
	}
	
	/**
	 * 
	 * @see edu.umd.cs.findbugs.BugReporter#getBugCollection()
	 */
	@Override
	public BugCollection getBugCollection() {
		return null;
	}
	
	
	/**
	 * 
	 * @see edu.umd.cs.findbugs.AbstractBugReporter#doReportBug(edu.umd.cs.findbugs.BugInstance)
	 */
	@Override
	protected void doReportBug(BugInstance bugInstance) {
		
		// RulePattern
		RulePatternCollection collection = RulePatternCollection.getInstance();
		RulePattern rulePattern = collection.get(AnalyzerEngine.FINDBUGS, bugInstance.getType());
		
		if (!rulePattern.isRegistered()) {
			rulePattern = new RulePattern(AnalyzerEngine.FINDBUGS, bugInstance.getType());
			rulePattern.setCategory(bugInstance.getCategoryAbbrev());
			rulePattern.setSeverity(toSeverity(bugInstance.getPriority()));
			
			collection.register(rulePattern);
		}
		
		
		// Violation
		SourceLineAnnotation primarySourceLineAnnotation = bugInstance.getPrimarySourceLineAnnotation();
		
		Violation violation = new Violation();
		violation.setRulePattern(rulePattern);
		violation.setFilename(primarySourceLineAnnotation.getSourcePath());
		violation.setBeginline(primarySourceLineAnnotation.getStartLine());
		violation.setEndline(primarySourceLineAnnotation.getEndLine());
		violation.setMessage(bugInstance.getMessageWithoutPrefix());
		
		ViolationResult.getInstance()
			.add(violation);
	}
	
	/**
	 * 
	 * @see edu.umd.cs.findbugs.AbstractBugReporter#reportAnalysisError(edu.umd.cs.findbugs.AnalysisError)
	 */
	@Override
	public void reportAnalysisError(AnalysisError error) {
		
	}
	
	/**
	 * 
	 * @see edu.umd.cs.findbugs.AbstractBugReporter#reportMissingClass(java.lang.String)
	 */
	@Override
	public void reportMissingClass(String string) {
		
	}
	
	
	private Severity toSeverity(int priority) {
		switch (priority) {
		case Priorities.HIGH_PRIORITY:
			return Severity.HIGH;
		
		case Priorities.NORMAL_PRIORITY:
			return Severity.MEDIUM;
		
		case Priorities.LOW_PRIORITY:
		case Priorities.EXP_PRIORITY:
		case Priorities.IGNORE_PRIORITY:
			return Severity.LOW;
		
		default:
			throw new IllegalArgumentException();
		}
	}
}

package com.tyn.jasca.engine.findbugs;

import com.tyn.jasca.Results;
import com.tyn.jasca.Violation;
import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.engine.SeverityLevel;
import com.tyn.jasca.rules.Pattern;
import com.tyn.jasca.rules.Rule;
import com.tyn.jasca.rules.RuleRepository;

import edu.umd.cs.findbugs.AbstractBugReporter;
import edu.umd.cs.findbugs.AnalysisError;
import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.SourceLineAnnotation;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;

/**
 * 
 * @author S.J.H.
 */
public class FindBugsBugReporter extends AbstractBugReporter {
	
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
		// Pattern
		Pattern pattern = Pattern.get(
				AnalyzerEngine.FINDBUGS,
				bugInstance.getType(),
				SeverityLevel.getSeverity(bugInstance.getPriority()));
		
		// Rule
		RuleRepository ruleCollection = RuleRepository.getInstance();
		Rule rule = ruleCollection.getRule(pattern);
		
		// Violation
		Violation violation = new Violation();
		violation.setPattern(pattern);
		violation.setRule(rule);
		
		SourceLineAnnotation primarySourceLineAnnotation = bugInstance.getPrimarySourceLineAnnotation();
		
		violation.setFilename(primarySourceLineAnnotation.getSourcePath());
		violation.setBeginline(primarySourceLineAnnotation.getStartLine());
		violation.setEndline(primarySourceLineAnnotation.getEndLine());
		violation.setMessage(bugInstance.getMessageWithoutPrefix());
		
		Results.getInstance()
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
}

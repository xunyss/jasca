package com.tyn.jasca.engine;

import net.sourceforge.pmd.RulePriority;

import com.tyn.jasca.Severity;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.Priority;

import edu.umd.cs.findbugs.Priorities;

/**
 * <pre>
 * +=======================+=======================+=======================+
 * | [JASCA]               | [FINDBUGS]            | [PMD]                 |
 * +=======================+=======================+=======================+
 * | HIGH                  | HIGH                  | HIGH                  |
 * +-----------------------+-----------------------+-----------------------+
 * | MEDIUM                | NORMAL                | MEDIUM_HIGH           |
 * +-----------------------+-----------------------+-----------------------+
 * | LOW                   | LOW                   | MEDIUM                |
 * |                       | EXP                   | MEDIUM_LOW            |
 * |                       | IGNORE *              | LOW *                 |
 * +=======================+=======================+=======================+
 * </pre>
 * 
 * @author S.J.H.
 * 
 * @ TODO 외부 설정으로 처리 가능하도록 수정
 */
public class SeverityLevel {
	
	/**
	 * JASCA Severity => FINDBUGS Priority
	 * 
	 * @param severity
	 * @return
	 */
	public static Priority getPriority(Severity severity) {
		switch (severity) {
			case HIGH:
				return Priority.HIGH;
				
			case MEDIUM:
				return Priority.NORMAL;
				
			case LOW:
				return Priority.IGNORE;
				
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * JASCA Severity => PMD RulePriority
	 * 
	 * @param severity
	 * @return
	 */
	public static RulePriority getRulePriority(Severity severity) {
		switch (severity) {
			case HIGH:
				return RulePriority.HIGH;
				
			case MEDIUM:
				return RulePriority.MEDIUM_HIGH;
				
			case LOW:
				return RulePriority.LOW;
				
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * FINDBUGS Priority => JASCA Severity
	 * 
	 * @param priority
	 * @return
	 */
	public static Severity getSeverity(Priority priority) {
		return getSeverity(priority.getPriorityThreshold());
	}
	
	/**
	 * FINDBUGS Priority => JASCA Severity
	 * 
	 * @param priority
	 * @return
	 */
	public static Severity getSeverity(int priority) {
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
	
	/**
	 * PMD RulePriority => JASCA Severity
	 * 
	 * @param rulePriority
	 * @return
	 */
	public static Severity getSeverity(RulePriority rulePriority) {
		switch (rulePriority) {
			case HIGH:
				return Severity.HIGH;
				
			case MEDIUM_HIGH:
			case MEDIUM:
				return Severity.MEDIUM;
			
			case MEDIUM_LOW:
			case LOW:
				return Severity.LOW;
			
			default:
				throw new IllegalArgumentException();
		}
	}
}

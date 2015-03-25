package com.tyn.jasca.findbugs.detector;

import org.apache.bcel.Constants;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * TODO: detect..
 * new Cookie(*, session.getId())
 * 
 * 
 * @author S.J.H.
 */
public class InfoExposureByCookiesDetector extends OpcodeStackDetector {

	private BugReporter bugReporter;
	
	public InfoExposureByCookiesDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}
	
	@Override
	public void sawOpcode(int seen) {
		if (seen == Constants.INVOKEINTERFACE
				&& getClassConstantOperand().equals("javax/servlet/http/HttpServletResponse")) {
			
			if (getNameConstantOperand().equals("addCookie")) {
					bugReporter.reportBug(new BugInstance(this, "INFO_EXPOSURE_BY_COOKIES", Priorities.NORMAL_PRIORITY)
						.addClass(this)
						.addMethod(this)
						.addSourceLine(this)
						.addString("XQConnection.prepareExpression()"));
			}
		}
	}
}

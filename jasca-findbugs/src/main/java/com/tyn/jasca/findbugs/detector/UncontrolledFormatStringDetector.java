package com.tyn.jasca.findbugs.detector;

import org.apache.bcel.Constants;

import com.h3xstream.findsecbugs.common.StringTracer;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * 
 * @author S.J.H.
 */
public class UncontrolledFormatStringDetector extends OpcodeStackDetector {
	
	private static final String BUGTYPE_UNCONTROL_FORMAT_STRING = "UNCONTROL_FORMAT_STRING";
	
	private BugReporter bugReporter;
	
	public UncontrolledFormatStringDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}
	
	@Override
	public void sawOpcode(int seen) {
		
		if (seen == Constants.INVOKESTATIC || seen == Constants.INVOKEVIRTUAL) {

			String methodName = getNameConstantOperand();
			if (methodName.equals("format") || methodName.equals("printf")) {
				
				int depth = stack.getStackDepth();
				if (depth == 3) {
					
					if (stack.getStackItem(0).getSignature().equals("[Ljava/lang/Object;")
							&& stack.getStackItem(1).getSignature().equals("Ljava/lang/String;")) {
						
						if (StringTracer.isVariableString(stack.getStackItem(1))) {
							bugReporter.reportBug(new BugInstance(this, BUGTYPE_UNCONTROL_FORMAT_STRING, Priorities.NORMAL_PRIORITY)
								.addClass(this)
								.addMethod(this)
								.addSourceLine(this));
						}
					}
				}
			}
		}
	}
}

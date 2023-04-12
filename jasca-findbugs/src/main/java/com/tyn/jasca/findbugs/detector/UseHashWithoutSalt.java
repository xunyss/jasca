package com.tyn.jasca.findbugs.detector;

import java.util.Iterator;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.ba.CFG;
import edu.umd.cs.findbugs.ba.CFGBuilderException;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.ba.Location;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * 
 * @author S.J.H.
 */
public class UseHashWithoutSalt extends OpcodeStackDetector {
	
	private static final String BUGTYPE_HASH_WITHOUT_SALT = "HASH_WITHOUT_SALT";
	
	private BugReporter bugReporter;
	
	public UseHashWithoutSalt(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}
	
	/**
	 * 
	 * @see edu.umd.cs.findbugs.bcel.OpcodeStackDetector#sawOpcode(int)
	 */
	@Override
	public void sawOpcode(int seen) {
		
		if (seen == INVOKEVIRTUAL) {
			
			if (getClassConstantOperand().equals("java/security/MessageDigest")
					&& getNameConstantOperand().equals("digest")) {
				
				ClassContext classContext = getClassContext();
				Method method = getMethod();
				
				try {
					analyzeMethod(classContext, method);
				}
				catch (CFGBuilderException cfge) {
					bugReporter.logError(BUGTYPE_HASH_WITHOUT_SALT, cfge);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param classContext
	 * @param method
	 */
	private void analyzeMethod(ClassContext classContext, Method method) throws CFGBuilderException {
		
		MethodGen methodGen = classContext.getMethodGen(method);
		if (methodGen == null) {
			return;
		}
		
		ConstantPoolGen cpg = methodGen.getConstantPool();
		CFG cfg = classContext.getCFG(method);
		
		//
		int hashCount = 0;
		
		for (Iterator<Location> i = cfg.locationIterator(); i.hasNext(); ) {
			
			Location location = i.next();
			InstructionHandle insHandle = location.getHandle();
			Instruction ins = insHandle.getInstruction();
			
			if (!(ins instanceof InvokeInstruction)) {
				continue;
			}
			
			InvokeInstruction invoke = (InvokeInstruction) ins;
			
			String methodName = invoke.getMethodName(cpg);
			String refType = invoke.getReferenceType(cpg).toString();
			String sig = invoke.getSignature(cpg);
			
			/** TODO INVOKEVIRTUAL 메소드를 호출한 reference 판단하는 방법 연구
			TypeDataflow typeDataflow = classContext.getTypeDataflow(method);
			TypeFrame frame = typeDataflow.getFactAtLocation(location);
			*/
			
			if (refType.equals("java.security.MessageDigest")) {
				
				if (methodName.equals("reset")) {
					hashCount = 0;
					continue;
				}
				
				if (methodName.equals("update")) {
					hashCount++;
					continue;
				}
				
				if (methodName.equals("digest")) {
					if ((sig.equals("()[B") ? 0 : 1) + hashCount < 2) {
						if (getPC() == insHandle.getPosition()) {
							bugReporter.reportBug(new BugInstance(this, BUGTYPE_HASH_WITHOUT_SALT, Priorities.NORMAL_PRIORITY)
								.addClass(this)
								.addMethod(this)
								.addSourceLine(this));
							
							break;
						}
						else {
							continue;
						}
					}
				}
			}
		}
	}
}

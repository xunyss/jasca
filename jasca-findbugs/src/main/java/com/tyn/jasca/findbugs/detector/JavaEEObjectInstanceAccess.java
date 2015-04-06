package com.tyn.jasca.findbugs.detector;

import java.util.HashSet;
import java.util.Set;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.FieldAnnotation;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * 
 * @see edu.umd.cs.findbugs.detect.MultithreadedInstanceAccess
 * 
 * @author S.J.H.
 */
public class JavaEEObjectInstanceAccess extends OpcodeStackDetector {

	private static final String BUGTYPE_SERVLET_INSTANCE_ACCESS = "SERVLET_INSTANCE_ACCESS";
	private static final String BUGTYPE_FILTER_INSTANCE_ACCESS = "FILTER_INSTANCE_ACCESS";
	
	private BugReporter bugReporter;
	
	public JavaEEObjectInstanceAccess(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}
	
	private static final String HTTP_SERVLET_NAME	= "javax.servlet.http.HttpServlet";
	private static final String SERVLET_NAME		= "javax.servlet.Servlet";
	private static final String FILTER_NAME			= "javax.servlet.Filter";
	
	private Set<JavaClass> mtClasses;
	
	private String mtClassName;
	
	private int monitorCount;
	
	private boolean writingField;
	
	private Set<String> alreadyReported;
	
	@Override
	public void sawOpcode(int seen) {
		
		if (seen == MONITORENTER) {
			monitorCount++;
		}
		else if (seen == MONITOREXIT) {
			monitorCount--;
		}

		writingField = ((seen == PUTFIELD) || (seen == PUTFIELD_QUICK) || (seen == PUTFIELD_QUICK_W));
	}
	
	@Override
	public void visitClassContext(ClassContext classContext) {
		try {
			JavaClass cls = classContext.getJavaClass();
			String superClsName = cls.getSuperclassName();
			String[] interfaceNames = cls.getInterfaceNames();
			
			if ((interfaceNames != null && interfaceNames.length == 0)
					&& "java.lang.Object".equals(superClsName)) {
				return;
			}
			
			
			if (HTTP_SERVLET_NAME.equals(superClsName)) {
				mtClassName = HTTP_SERVLET_NAME;
				super.visitClassContext(classContext);
			}
			else if (hasItem(interfaceNames, SERVLET_NAME)) {
				mtClassName = SERVLET_NAME;
				super.visitClassContext(classContext);
			}
			else if (hasItem(interfaceNames, FILTER_NAME)) {
				mtClassName = FILTER_NAME;
				super.visitClassContext(classContext);
			}
			else {
				for (JavaClass mtClass : getMtClasses()) {
					/*
					 * note: We could just call cls.instanceOf(mtClass) and it
					 * would work for both classes and interfaces, but if
					 * mtClass is an interface it is more efficient to call
					 * cls.implementationOf() and since we're doing this on each
					 * visit that's what we'll do. also note:
					 * implementationOf(mtClass) throws an
					 * IllegalArgumentException when mtClass is not an
					 * interface. See bug#1428253.
					 */
					if (mtClass.isClass() ? cls.instanceOf(mtClass) : cls.implementationOf(mtClass)) {
						mtClassName = mtClass.getClassName();
						super.visitClassContext(classContext);
						return;
					}
				}
			}
		}
		catch (Exception e) {
			// already reported
		}
	}

	@Override
	public void visitMethod(Method obj) {
		monitorCount = 0;
		alreadyReported = new HashSet<String>();
		writingField = false;
	}


	@Override
	public boolean shouldVisitCode(Code code) {
		return !"<init>".equals(getMethodName()) && !"init".equals(getMethodName());

	}

	@Override
	public void sawField() {
		if ((monitorCount > 0) || (!writingField)) {
			return;
		}

		ConstantFieldref fieldRef;
		Constant c = getConstantRefOperand();
		if (c instanceof ConstantFieldref) {
			fieldRef = (ConstantFieldref) c;

			String className = fieldRef.getClass(getConstantPool()).replace('.', '/');
			if (className.equals(this.getClassName())) {
				ConstantPool cp = getConstantPool();
				int nameAndTypeIdx = fieldRef.getNameAndTypeIndex();
				ConstantNameAndType ntc = (ConstantNameAndType) cp.getConstant(nameAndTypeIdx);
				int nameIdx = ntc.getNameIndex();

				Field[] flds = getClassContext().getJavaClass().getFields();

				for (Field fld : flds) {
					if (fld.getNameIndex() == nameIdx) {
						if (!fld.isStatic()) {
							ConstantUtf8 nameCons = (ConstantUtf8) cp.getConstant(nameIdx);
							ConstantUtf8 typeCons = (ConstantUtf8) cp.getConstant(ntc.getSignatureIndex());

							if (alreadyReported.contains(nameCons.getBytes())) {
								return;
							}
							alreadyReported.add(nameCons.getBytes());
							
							bugReporter.reportBug(new BugInstance(this,
									FILTER_NAME.equals(mtClassName) ? BUGTYPE_FILTER_INSTANCE_ACCESS : BUGTYPE_SERVLET_INSTANCE_ACCESS,
									LOW_PRIORITY)
								.addField(
									new FieldAnnotation(getDottedClassName(), nameCons.getBytes(), typeCons.getBytes(),
											false)).addClass(this).addSourceLine(this));
						}
						break;
					}
				}
			}
		}
	}
	
	private Set<JavaClass> getMtClasses() {
		if (mtClasses != null) {
			return mtClasses;
		}

		mtClasses = new HashSet<JavaClass>();
		
		try {
			mtClasses.add(Repository.lookupClass(HTTP_SERVLET_NAME));
		} catch (ClassNotFoundException cnfe) {
			// probably would be annoying to report
		}
		try {
			mtClasses.add(Repository.lookupClass(SERVLET_NAME));
		} catch (ClassNotFoundException cnfe) {
			// probably would be annoying to report
		}
		try {
			mtClasses.add(Repository.lookupClass(FILTER_NAME));
		} catch (ClassNotFoundException cnfe) {
			// probably would be annoying to report
		}
		
		return mtClasses;
	}
	
	private boolean hasItem(Object[] arr, Object obj) {
		if (arr != null && arr.length > 0) {
			for (Object item : arr) {
				if (item.equals(obj)) {
					return true;
				}
			}
		}
		
		return false;
	}
}

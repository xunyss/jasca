package com.tyn.jasca.pmd.rule;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

/**
 * 
 * @author S.J.H.
 */
public class DynamicClassLoadingRule extends AbstractJavaRule {

	@Override
	public Object visit(ASTPrimaryExpression node, Object data) {
		
		ASTPrimaryPrefix prefix = (ASTPrimaryPrefix) node.jjtGetChild(0);
		
		if (prefix.jjtGetNumChildren() > 0) {
			Node name = prefix.jjtGetChild(0);
			
			if (name instanceof ASTName) {
				if ("Class.forName".equals(name.getImage())) {
					
					// violation
					addViolation(data, node);
				}
			}
		}
			
		return data;
	}
}

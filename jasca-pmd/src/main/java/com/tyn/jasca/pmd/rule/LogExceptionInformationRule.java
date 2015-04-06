package com.tyn.jasca.pmd.rule;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTCatchStatement;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import org.jaxen.JaxenException;

/**
 * 
 * @author S.J.H.
 */
public class LogExceptionInformationRule extends AbstractJavaRule {

	/**
	 * 
	 * @see net.sourceforge.pmd.lang.java.rule.AbstractJavaRule#visit(net.sourceforge.pmd.lang.java.ast.ASTCatchStatement, java.lang.Object)
	 */
	@Override
	public Object visit(ASTCatchStatement node, Object data) {
		
		try {
			@SuppressWarnings("unchecked")
			List<ASTVariableDeclaratorId> exceptionIdList = node.findChildNodesWithXPath(".//VariableDeclaratorId");
			
			Set<String> exceptionIdSet = new HashSet<String>();
			
			for (ASTVariableDeclaratorId exceptionId : exceptionIdList) {
				exceptionIdSet.add(exceptionId.getImage());
			}
			
			if (node.jjtGetNumChildren() > 1) {
				// CatchStatement/Block
				checkChildrens(node.jjtGetChild(1), exceptionIdSet, data);
			}
		}
		catch (JaxenException je) {
			throw new IllegalStateException(je);
		}
		
		return data;
	}
	
	/**
	 * 
	 * @param catchBlockNode
	 * @param exceptionIdSet
	 * @param data
	 * @throws JaxenException
	 */
	private void checkChildrens(Node catchBlockNode, Set<String> exceptionIdSet, Object data) throws JaxenException {
		
		@SuppressWarnings("unchecked")
		List<Node> nodes = (List<Node>) catchBlockNode.findChildNodesWithXPath(
				".//PrimaryPrefix/Name[@Image = \"System.out.println\" or @Image = \"System.err.println\"]");
		
		for (Node node : nodes) {
			@SuppressWarnings("unchecked")
			List<Node> names = (List<Node>) node.jjtGetParent().jjtGetParent()
					.findChildNodesWithXPath("./PrimarySuffix/Arguments/.//Name");
			
			for (Node name : names) {
				
				if (name instanceof ASTName) {
					
					Iterator<String> exRefItr = exceptionIdSet.iterator();
					while (exRefItr.hasNext()) {
						String image = name.getImage();
						String exRef = exRefItr.next();
						
						if (image.startsWith(exRef)) {
							if (image.equals(exRef)
									|| image.endsWith(".toString")
									|| image.endsWith(".getMessage")) {
							
								// violation
								addViolation(data, node, name.getImage());
							}
						}
					}
				}
			}
		}
	}
}

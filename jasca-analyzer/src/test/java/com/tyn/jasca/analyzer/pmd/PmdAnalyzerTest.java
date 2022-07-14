package com.tyn.jasca.analyzer.pmd;

import net.sourceforge.pmd.RulePriority;

import org.junit.Test;

import com.tyn.jasca.analyzer.pmd.PmdConstant.RenderFormat;

/**
 * 
 * @author S.J.H.
 */
public class PmdAnalyzerTest {
	
	String rulesets = "java-basic,java-design,jsp-basic" +
			",java-codesize,java-empty,java-j2ee,java-javabeans,java-migrating" +
			",java-strings,java-sunsecure,java-typeresolution,java-braces,java-clone,java-comments,java-strictexception" +
			",jasca-jasca";
	
//	@Test
	public void pmdTest() {
		PmdConfiguration config = new PmdConfiguration();
		config.setDir("D:/securecoding/workspace/sampleweb");
		config.setMinimumpriority(RulePriority.LOW);
		config.setFormat(RenderFormat.HTML);
		config.setProgress(true);
		config.setReportfile("D:/xdev/git/jasca/jasca-analyzer/target/analyzer-pmd.html");
		config.setRulesets(rulesets);
		
		PmdAnalyzer engine = new PmdAnalyzer();
		engine.applyConfiguration(config);
		engine.execute();
	}
}

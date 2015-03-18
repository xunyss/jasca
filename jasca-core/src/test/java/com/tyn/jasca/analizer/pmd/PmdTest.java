package com.tyn.jasca.analizer.pmd;

import net.sourceforge.pmd.RulePriority;

import org.junit.Test;

import com.tyn.jasca.analyzer.pmd.PmdAnalyzer;
import com.tyn.jasca.analyzer.pmd.PmdConfiguration;
import com.tyn.jasca.analyzer.pmd.PmdConstant.ReportFormat;

/**
 * 
 * @author S.J.H.
 */
public class PmdTest {
	
	String rulesets = "java-basic,java-design,jsp-basic" +
			",java-codesize,java-empty,java-j2ee,java-javabeans,java-migrating" +
			",java-strings,java-sunsecure,java-typeresolution,java-braces,java-clone,java-comments,java-strictexception" +
			",jasca-jasca";
	
	@Test
	public void pmdTest() {
		PmdConfiguration config = new PmdConfiguration();
		config.setDir("D:/securecoding/workspace/sampleweb");
	//	config.setDir("D:/securecoding/workspace/sampleweb/src/com/tyn/wweb/W47.java");
		config.setMinimumpriority(RulePriority.LOW);
		config.setFormat(ReportFormat.HTML);
	//	config.setRenderer(JascaRenderer.class);
		config.setProgress(true);
//		config.setProgress(new PmdProgress() {
//			
//		});
		config.setReportfile("D:/securecoding/workspace/jasca-core/target/pmd.html");
		config.setRulesets(rulesets);
		
		PmdAnalyzer engine = new PmdAnalyzer();
		engine.applyConfiguration(config);
		engine.execute();
	}
}

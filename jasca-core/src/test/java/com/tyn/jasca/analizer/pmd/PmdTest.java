package com.tyn.jasca.analizer.pmd;

import net.sourceforge.pmd.RulePriority;

import org.junit.Test;

import com.tyn.jasca.analyzer.pmd.PmdAnalyzer;
import com.tyn.jasca.analyzer.pmd.PmdConfiguration;
import com.tyn.jasca.analyzer.pmd.PmdConstant.ReportFormat;
import com.tyn.jasca.analyzer.pmd.PmdProgress;

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
		config.setProgressCallback(new PmdProgress() {
			@Override
			public void startAnalyze(int totalCount) {
				System.out.println("111111111111111111111111 >> " + totalCount);
			}
			@Override
			public void analyzeFile(int passCount) {
				System.out.println("333333333333333333333333 >> " + passCount);
			}
			@Override
			public void finishAnalyze() {
				System.out.println("5555555555555555555555555");
			}
			
		});
		config.setReportfile("D:/xdev/git/jasca/jasca-core/target/pmd.html");
		config.setRulesets(rulesets);
		
		PmdAnalyzer engine = new PmdAnalyzer();
		engine.applyConfiguration(config);
		engine.execute();
	}
}

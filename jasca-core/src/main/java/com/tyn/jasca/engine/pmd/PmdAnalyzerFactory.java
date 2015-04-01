package com.tyn.jasca.engine.pmd;

import com.tyn.jasca.JascaConfiguration;
import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.analyzer.pmd.PmdAnalyzer;
import com.tyn.jasca.analyzer.pmd.PmdConfiguration;
import com.tyn.jasca.analyzer.pmd.PmdConstant.RenderFormat;
import com.tyn.jasca.engine.AnalyzerFactory;
import com.tyn.jasca.engine.SeverityLevel;

/**
 * 
 * @author S.J.H.
 */
public class PmdAnalyzerFactory implements AnalyzerFactory {
	
	private static class Holder {
		private static final AnalyzerFactory instance = new PmdAnalyzerFactory();
	}
	
	public static AnalyzerFactory getInstance() {
		return Holder.instance;
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.AnalyzerFactory#getAnalyzer(com.tyn.jasca.JascaConfiguration)
	 */
	public Analyzer getAnalyzer(JascaConfiguration jascaConfiguration) {
		
		String rulesets = "java-basic,java-design,jsp-basic" +
				",java-codesize,java-empty,java-j2ee,java-javabeans,java-migrating,java-comments" +
				",java-strings,java-sunsecure,java-typeresolution,java-braces,java-clone,java-strictexception" +
				",jasca-jasca";
		
		PmdConfiguration pmdConfiguration = new PmdConfiguration();
		
		pmdConfiguration.setRulesets(rulesets);
		pmdConfiguration.setFormat(RenderFormat.CUSTOM);
		pmdConfiguration.setRenderer(PmdViolationRenderer.class);
		
		pmdConfiguration.setDebug(jascaConfiguration.isDebug());
		pmdConfiguration.setMinimumpriority(SeverityLevel.getRulePriority(jascaConfiguration.getSeverity()));
		if (jascaConfiguration.isProgress()) {
			pmdConfiguration.setProgress(jascaConfiguration.getProgressCallback());
		}
		pmdConfiguration.setDir(jascaConfiguration.getTarget());
		
		PmdAnalyzer engine = new PmdAnalyzer();
		engine.applyConfiguration(pmdConfiguration);
		
		return engine;
	}
}

package com.tyn.jasca.engine.findbugs;

import com.tyn.jasca.JascaConfiguration;
import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.analyzer.findbugs.FindBugsAnalyzer;
import com.tyn.jasca.analyzer.findbugs.FindBugsConfiguration;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.ReportFormat;
import com.tyn.jasca.common.TempFileManager;
import com.tyn.jasca.engine.AnalyzerFactory;
import com.tyn.jasca.engine.SeverityLevel;

/**
 * 
 * @author S.J.H.
 */
public class FindBugsAnalyzerFactory implements AnalyzerFactory {
	
	private static class Holder {
		private static final AnalyzerFactory instance = new FindBugsAnalyzerFactory();
	}
	
	public static AnalyzerFactory getInstance() {
		return Holder.instance;
	}
	
	
	private static final String DEFAULT_FILTER = "jasca-filter-findbugs.xml";
	
	/**
	 * 
	 * @see com.tyn.jasca.AnalyzerFactory#getAnalyzer(com.tyn.jasca.JascaConfiguration)
	 */
	public Analyzer getAnalyzer(JascaConfiguration jascaConfiguration) {
		
		FindBugsConfiguration findbugsConfiguration = new FindBugsConfiguration();
		
		findbugsConfiguration.setExclude(TempFileManager.createTempFilepathFromResource(DEFAULT_FILTER));
		findbugsConfiguration.setScanNestedArchives(false);
		findbugsConfiguration.setReportFormat(ReportFormat.CUSTOM);
		findbugsConfiguration.setBugReporter(new FindBugsBugReporter());
		
		findbugsConfiguration.setDebug(jascaConfiguration.isDebug());
		findbugsConfiguration.setPriority(SeverityLevel.getPriority(jascaConfiguration.getSeverity()));
		if (jascaConfiguration.isProgress()) {
			findbugsConfiguration.setProgress(jascaConfiguration.getProgressCallback());
		}
		findbugsConfiguration.setInput(jascaConfiguration.getTarget());
		
		FindBugsAnalyzer engine = new FindBugsAnalyzer();
		engine.loadPluginUsingClass("com.h3xstream.findsecbugs.endpoint.CookieDetector");	// find security bugs
	//	engine.loadPluginUsingClass(com.tyn.jasca.findbugs.detector.FindMe.class);			// jasca-findbugs
		engine.loadPluginUsingJarFilePath("D:/xdev/git/jasca/jasca-findbugs/target/jasca-findbugs-0.0.1-SNAPSHOT.jar");
		engine.applyConfiguration(findbugsConfiguration);
		
		// find security bugs
		String path = getClass().getResource("/findsecbugs/custom-injection/frameplus.properties").getPath();
		System.setProperty("findsecbugs.injection.sources", path);
		
		return engine;
	}
}

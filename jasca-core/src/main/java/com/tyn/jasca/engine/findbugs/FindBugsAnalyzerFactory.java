package com.tyn.jasca.engine.findbugs;

import com.tyn.jasca.Jasca;
import com.tyn.jasca.JascaConfiguration;
import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.analyzer.findbugs.FindBugsAnalyzer;
import com.tyn.jasca.analyzer.findbugs.FindBugsConfiguration;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.ReportFormat;
import com.tyn.jasca.common.TempFileManager;
import com.tyn.jasca.engine.AnalyzerFactory;
import com.tyn.jasca.engine.SeverityLevel;
import com.tyn.jasca.findbugs.JascaFindBugsPlugin;

import edu.umd.cs.findbugs.FindBugs;

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
		
		// load plug-in
		FindBugsAnalyzer engine = new FindBugsAnalyzer();
		engine.loadPluginUsingClass("com.h3xstream.findsecbugs.endpoint.CookieDetector");	// find security bugs
	//	engine.loadPluginUsingClass(FBContrib.class);										// fb-contrib
		loadJascaFindBugsPlugin(engine);
		
		// find security bugs > CustomInjection
		String customFrameplus = getClass().getClassLoader()
				.getResource("findsecbugs/custom-injection/frameplus.properties").getPath();
		System.setProperty("findsecbugs.injection.sources", customFrameplus);
		
		// apply configuration
		engine.applyConfiguration(findbugsConfiguration);
		return engine;
	}
	
	/**
	 * 
	 * @param engine
	 */
	private void loadJascaFindBugsPlugin(FindBugsAnalyzer engine) {
		String url = FindBugs.class.getClassLoader()
				.getResource(JascaFindBugsPlugin.class.getName().replace('.', '/') + ".class").toString();
		
		// for release
		if (url.startsWith("jar:file:")) {
			engine.loadPluginUsingClass(JascaFindBugsPlugin.class);
		}
		// for debugging
		else {
			String path = "D:/xdev/git/jasca/jasca-findbugs/target/";
			String jar = "jasca-findbugs-" + Jasca.VERSION + ".jar";
			engine.loadPluginUsingJarFilePath(path + jar);
		}
	}
}

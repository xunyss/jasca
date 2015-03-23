package com.tyn.jasca.analyzer.findbugs;

import org.junit.Test;

import com.tyn.jasca.analyzer.findbugs.FindBugsAnalyzer;
import com.tyn.jasca.analyzer.findbugs.FindBugsConfiguration;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.Priority;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.ReportFormat;

/**
 * 
 * @author S.J.H.
 */
public class FindBugsAnalyzerTest {
	
	@Test
	public void findbugsTest() {
		FindBugsConfiguration config = new FindBugsConfiguration();
		config.setSystemOption();
		config.setScanNestedArchives(false);
		config.setPriority(Priority.MEDIUM);
		config.setReportFormat(ReportFormat.HTML);
		config.setOutput("D:/xdev/git/jasca/jasca-analyzer/target/analyzer-findbugs.html");
		config.setInput("D:/securecoding/workspace/sampleweb");
		config.setProgress(true);
		
		FindBugsAnalyzer engine = new FindBugsAnalyzer();
		
		engine.loadPluginUsingClass("com.h3xstream.findsecbugs.endpoint.CookieDetector");
		engine.loadPluginUsingJarFilePath("D:/xdev/git/jasca/jasca-findbugs/target/jasca-findbugs-0.0.1-SNAPSHOT.jar");
		System.out.println(engine.getPluginsInformation());
		
		engine.applyConfiguration(config);
		engine.execute();
	}
}

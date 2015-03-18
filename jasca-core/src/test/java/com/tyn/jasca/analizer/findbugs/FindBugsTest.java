package com.tyn.jasca.analizer.findbugs;

import org.junit.Test;

import com.tyn.jasca.TempProgress;
import com.tyn.jasca.analyzer.findbugs.FindBugsAnalyzer;
import com.tyn.jasca.analyzer.findbugs.FindBugsConfiguration;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.Priority;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.ReportFormat;

/**
 * 
 * @author S.J.H.
 */
public class FindBugsTest {
	
	@Test
	public void findbugsTest() {
		FindBugsConfiguration config = new FindBugsConfiguration();
		config.setSystemOption();
		config.setScanNestedArchives(false);
		config.setPriority(Priority.MEDIUM);
		config.setReportFormat(ReportFormat.HTML);
		config.setOutput("D:/securecoding/workspace/jasca-core/target/findbugs.html");
		config.setInput("D:/securecoding/workspace/sampleweb");
	//	config.setInput("D:/securecoding/workspace/sampleweb/build/classes/com/tyn/wweb/W15.class");
		config.setProgress(true);
		
		FindBugsAnalyzer engine = new FindBugsAnalyzer();
		
		engine.loadPluginUsingClass("com.h3xstream.findsecbugs.endpoint.CookieDetector");
		engine.loadPluginUsingJarFilePath("D:/securecoding/workspace/jasca-findbugs/target/jasca-findbugs-0.0.1-SNAPSHOT.jar");
		System.out.println(engine.getPluginsInformation());
		
		engine.applyConfiguration(config);
		engine.execute();
	}
}

package com.tyn.jasca.analizer.findbugs;

import org.junit.Test;

import com.tyn.jasca.analyzer.findbugs.FindBugsAnalyzer;

public class FindBugsPluginsTest {

	@Test
	public void showFindbugsPlugins() throws Exception {
		FindBugsAnalyzer engine = new FindBugsAnalyzer();
		
		// find security bug
		engine.loadPluginUsingClass("com.h3xstream.findsecbugs.endpoint.CookieDetector");
		
		// jasca
		engine.loadPluginUsingJarFilePath("D:/securecoding/workspace/jasca-findbugs/target/jasca-findbugs-0.0.1-SNAPSHOT.jar");
		
		String plugins = engine.getPluginsInformation();
		System.out.println(plugins);
	}
}

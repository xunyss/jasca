package com.tyn.jasca.analyzer.findbugs;

import org.junit.Test;

import com.tyn.jasca.analyzer.findbugs.FindBugsAnalyzer;

/**
 * 
 * @author S.J.H.
 */
public class FindBugsPluginsTest {

	@Test
	public void showFindbugsPlugins() throws Exception {
		FindBugsAnalyzer engine = new FindBugsAnalyzer();
		
		// find security bug
		engine.loadPluginUsingClass("com.h3xstream.findsecbugs.endpoint.CookieDetector");
		
		// JASCA
		engine.loadPluginUsingJarFilePath("D:/xdev/git/jasca/jasca-findbugs/target/jasca-findbugs-0.0.1-SNAPSHOT.jar");
		
		String plugins = engine.getPluginsInformation();
		System.out.println(plugins);
	}
}

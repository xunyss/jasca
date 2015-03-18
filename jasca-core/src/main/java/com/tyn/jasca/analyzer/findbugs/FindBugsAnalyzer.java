package com.tyn.jasca.analyzer.findbugs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.analyzer.Configuration;
import com.tyn.jasca.common.Utils;

import edu.umd.cs.findbugs.FindBugs;
import edu.umd.cs.findbugs.FindBugs2;
import edu.umd.cs.findbugs.IFindBugsEngine;
import edu.umd.cs.findbugs.Plugin;
import edu.umd.cs.findbugs.PluginException;

/**
 * 
 * @author S.J.H.
 */
public class FindBugsAnalyzer extends Analyzer {
	
	/**
	 * 
	 */
	private IFindBugsEngine internalEngine = null;
	
	/**
	 * 
	 */
	public FindBugsAnalyzer() {
		internalEngine = new FindBugs2();
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.analyzer.Analyzer#applyConfiguration(com.tyn.jasca.analyzer.Configuration)
	 */
	@Override
	public void applyConfiguration(Configuration configuration) {
		((FindBugsConfiguration) configuration)
			.configureEngine(internalEngine);
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.analyzer.Analyzer#execute()
	 * @see edu.umd.cs.findbugs.FindBugs#runMain(IFindBugsEngine findBugs, TextUICommandLine commandLine)
	 */
	@Override
	public void execute() {
		try {
			internalEngine.execute();
			
			/*
			int bugCount			= internalEngine.getBugCount();
			int missingClassCount	= internalEngine.getMissingClassCount();
			int errorCount			= internalEngine.getErrorCount();
			
			System.err.println(bugCount);
			System.err.println(missingClassCount);
			System.err.println(errorCount);
			*/
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param jarFileUrl
	 */
	public void loadPluginUsingJarFilePath(URL jarFileUrl) {
		try {
			Plugin.loadCustomPlugin(jarFileUrl, null);
		}
		catch (PluginException pe) {
			pe.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param jarFilePath
	 */
	public void loadPluginUsingJarFilePath(String jarFilePath) {
		if (!jarFilePath.startsWith("file:/")) {
			jarFilePath = "file:/" + jarFilePath;
		}
		
		try {
			loadPluginUsingJarFilePath(new URL(jarFilePath));
		}
		catch (MalformedURLException murle) {
			murle.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param className
	 */
	public void loadPluginUsingClass(String className) {
		String detectorClassName = Utils.getSlashedClassName(className);
		String detectorClassURL = FindBugs.class.getClassLoader()
				.getResource(detectorClassName + ".class").toString();
		
		String jarFilePath = detectorClassURL.substring(4, detectorClassURL.indexOf("!/"));
		loadPluginUsingJarFilePath(jarFilePath);
	}
	
	/**
	 * 
	 * @param clazz
	 */
	public void loadPluginUsingClass(Class<?> clazz) {
		loadPluginUsingClass(clazz.getName());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPluginsInformation() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Plugin p : Plugin.getAllPlugins()) {
			stringBuilder.append("Plugin: " + p.getPluginId());
			stringBuilder.append("\n  description: " + p.getShortDescription());
			stringBuilder.append("\n     provider: " + p.getProvider());
			String version = p.getVersion();
			if ((version != null) && (version.length() > 0)) {
				stringBuilder.append("\n      version: " + version);
			}
			String website = p.getWebsite();
			if ((website != null) && (website.length() > 0)) {
				stringBuilder.append("\n      website: " + website);
			}
			stringBuilder.append('\n');
		}
		
		return stringBuilder.toString();
	}
}

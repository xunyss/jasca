package com.tyn.jasca.analyzer.sonar;

import java.util.Properties;

/**
 * 
 * @author S.J.H.
 */
public class SonarRunnerTest {
	
	public static final String ANALISYS_MODE		= "sonar.analysis.mode";
	public static final String SCM_ENABLED			= "sonar.scm.enabled";
	public static final String SCM_STAT_ENABLED		= "sonar.scm-stats.enabled";
	public static final String ISSUEASSIGN_PLUGIN	= "issueassignplugin.enabled";
	public static final String EXPORT_PATH			= "sonar.report.export.path";
	public static final String VERBOSE				= "sonar.verbose";
	public static final String INCLUDE_FILES		= "sonar.inclusions";
	public static final String WORKDIR				= "sonar.working.directory";
	public static final String PROJECT_BASEDIR		= "sonar.projectBaseDir";
	
	public static void main(String[] args) throws Exception {
		
		Properties props = new Properties();
		
		props.put("sonar.host.url", "http://localhost:9000");
		props.put("sonar.jdbc.url", "jdbc:oracle:thin:@10.10.71.112:1521/TWB01DB");
		props.put("sonar.jdbc.username", "xuny");
		props.put("sonar.jdbc.password", "xuny");
		props.put("sonar.login", "admin");
		props.put("sonar.password", "admin");
		
		// default : "analysis" ("incremental")
		props.put("sonar.analysis.mode", "preview");
	//	props.put("sonar.scm.enabled", "false");
	//	props.put("sonar.scm-stats.enabled", "false");
		props.put("sonar.working.directory", ".sonar");
		props.put("sonar.report.export.path", "testoutput.json");
		
		props.put("sonar.projectKey", "prj:sampleweb");
		props.put("sonar.projectName", "SampleWeb");
		props.put("sonar.projectVersion", "1.0");
		props.put("sonar.projectBaseDir", "D:/securecoding/workspace/sampleweb");
		props.put("sonar.sources", "./src");
		props.put("sonar.binaries", "./build/classes");
		props.put("sonar.libraries", "./lib/*.jar");
		props.put("sonar.language", "java");
		
		
		/*
		EmbeddedRunner sonarEmbeddedRunner = EmbeddedRunner.create();
		
		sonarEmbeddedRunner.addProperties(props);
		sonarEmbeddedRunner.execute();
		*/
	}
}

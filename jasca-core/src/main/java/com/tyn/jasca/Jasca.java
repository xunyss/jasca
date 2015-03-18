package com.tyn.jasca;

import net.sourceforge.pmd.RulePriority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.analyzer.findbugs.FindBugsAnalyzer;
import com.tyn.jasca.analyzer.findbugs.FindBugsConfiguration;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.Priority;
import com.tyn.jasca.analyzer.pmd.PmdAnalyzer;
import com.tyn.jasca.analyzer.pmd.PmdConfiguration;
import com.tyn.jasca.analyzer.pmd.PmdConstant;


/**
 * 
 * @author S.J.H.
 */
public class Jasca {
	
	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(Jasca.class);
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String target = args[0];
		String output = args[1];
		
		Jasca jasca = new Jasca();
		jasca.setTarget(target);
		jasca.setOutput(output);
		jasca.run();
	}
	
	
	private String target = null;
	private String output = null;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void run() {
		try {
			runFindbugs();
			runPmd();
			Htmler.make();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void runFindbugs() {
		FindBugsConfiguration configuration = new FindBugsConfiguration();
		configuration.setScanNestedArchives(false);
		configuration.setPriority(Priority.LOW);
		configuration.setReportFormat(FindBugsConstant.ReportFormat.CUSTOM);
		configuration.setBugReporter(new JascaBugReporter());
		configuration.setOutput(output);
		configuration.setProgress(true);
		configuration.setInput(target);
		
		FindBugsAnalyzer engine = new FindBugsAnalyzer();
		engine.loadPluginUsingClass("com.h3xstream.findsecbugs.endpoint.CookieDetector");
		engine.loadPluginUsingJarFilePath("D:/securecoding/workspace/jasca-findbugs/target/jasca-findbugs-0.0.1-SNAPSHOT.jar");
		engine.applyConfiguration(configuration);
		
		log.info("JASCA - FindBugs start");
		engine.execute();
		log.info("JASCA - FindBugs finish");
	}
	
	private void runPmd() {
		String rulesets = "java-basic,java-design,jsp-basic" +
				",java-codesize,java-empty,java-j2ee,java-javabeans,java-migrating" +
				",java-strings,java-sunsecure,java-typeresolution,java-braces,java-clone,java-comments,java-strictexception" +
				",jasca-jasca";
		
		PmdConfiguration config = new PmdConfiguration();
		config.setMinimumpriority(RulePriority.LOW);
		config.setFormat(PmdConstant.ReportFormat.CUSTOM);
		config.setRenderer(JascaRenderer.class);
		config.setReportfile(output);
		config.setProgress(true);
		config.setRulesets(rulesets);
		config.setDir(target);
		
		PmdAnalyzer engine = new PmdAnalyzer();
		engine.applyConfiguration(config);
		
		log.info("JASCA - PMD start");
		engine.execute();
		log.info("JASCA - PMD finish");
	}
}

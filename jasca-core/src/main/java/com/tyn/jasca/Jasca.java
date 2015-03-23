package com.tyn.jasca;

import net.sourceforge.pmd.RulePriority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.analyzer.findbugs.FindBugsAnalyzer;
import com.tyn.jasca.analyzer.findbugs.FindBugsConfiguration;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.Priority;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.ReportFormat;
import com.tyn.jasca.analyzer.pmd.PmdAnalyzer;
import com.tyn.jasca.analyzer.pmd.PmdConfiguration;
import com.tyn.jasca.analyzer.pmd.PmdConstant.RenderFormat;


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
			ViolationResult violationResult = ViolationResult.getInstance();
			violationResult.create();
			
			runFindbugs();
			runPmd();
			
			ReportBuilder.build(
					violationResult.getViolations(),
					new JascaConverter(),
					new HtmlFormatter(),
					target, output);
			
			violationResult.clear();
			
			log.debug("ºÐ¼®³¡³µÀ½.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JascaProgress progressCallback = new JascaProgress();
	
	private void runFindbugs() {
		FindBugsConfiguration configuration = new FindBugsConfiguration();
		configuration.setInput(target);
		configuration.setScanNestedArchives(false);
		configuration.setPriority(Priority.IGNORE);
		configuration.setReportFormat(ReportFormat.CUSTOM);
		configuration.setBugReporter(new JascaBugReporter());
		configuration.setProgress(progressCallback);
		
		FindBugsAnalyzer engine = new FindBugsAnalyzer();
		engine.loadPluginUsingClass("com.h3xstream.findsecbugs.endpoint.CookieDetector");
		engine.loadPluginUsingJarFilePath("D:/xdev/git/jasca/jasca-findbugs/target/jasca-findbugs-0.0.1-SNAPSHOT.jar");
		engine.applyConfiguration(configuration);
		engine.execute();
	}
	
	private void runPmd() {
		String rulesets = "java-basic,java-design,jsp-basic" +
				",java-codesize,java-empty,java-j2ee,java-javabeans,java-migrating" +
				",java-strings,java-sunsecure,java-typeresolution,java-braces,java-clone,java-comments,java-strictexception" +
				",jasca-jasca";
		
		PmdConfiguration config = new PmdConfiguration();
		config.setDir(target);
		config.setMinimumpriority(RulePriority.LOW);
		config.setFormat(RenderFormat.CUSTOM);
		config.setRenderer(JascaRenderer.class);
		config.setProgress(progressCallback);
		config.setRulesets(rulesets);
		
		PmdAnalyzer engine = new PmdAnalyzer();
		engine.applyConfiguration(config);
		engine.execute();
	}
}

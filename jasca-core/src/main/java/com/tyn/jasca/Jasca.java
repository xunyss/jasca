package com.tyn.jasca;

import java.io.PrintWriter;
import java.util.List;

import net.sourceforge.pmd.RulePriority;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.analyzer.findbugs.FindBugsAnalyzer;
import com.tyn.jasca.analyzer.findbugs.FindBugsConfiguration;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.Priority;
import com.tyn.jasca.analyzer.findbugs.FindBugsConstant.ReportFormat;
import com.tyn.jasca.analyzer.pmd.PmdAnalyzer;
import com.tyn.jasca.analyzer.pmd.PmdConfiguration;
import com.tyn.jasca.analyzer.pmd.PmdConstant.RenderFormat;
import com.tyn.jasca.engine.JascaConverter;
import com.tyn.jasca.engine.JascaProgress;
import com.tyn.jasca.engine.findbugs.JascaBugReporter;
import com.tyn.jasca.engine.pmd.JascaRenderer;


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
	 * @return
	 */
	public static final Logger getLogger() {
		return log;
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		JascaConfiguration jascaConfiguration = new JascaConfiguration();
		
		Options options = new Options();
		options.addOption("l", true, "분석 심각도 레벨 [high|medium(default)|low]");
		options.addOption("f", true, "레포트 포맷 [html(default)|xml|xls]");
		options.addOption("o", true, "레포트 결과 저장 파일 명");
		
		try {
			CommandLineParser parser = new BasicParser();
			CommandLine command = parser.parse(options, args);
			
			/**
			 * JascaConfiguration setup
			 */
			JascaConfiguration.setup(jascaConfiguration, command);
		}
		catch (ParseException pe) {
			PrintWriter pw = new PrintWriter(System.out);
			pw.write(pe.getMessage());
			pw.write(IOUtils.LINE_SEPARATOR);
			pw.flush();
			
			HelpFormatter help = new HelpFormatter();
			help.printHelp("jasca [options] [분석대상 파일 또는 디렉토리]", options);
			
			return;		// exit
		}
		
		/**
		 * execute JASCA
		 */
		
		Jasca jasca = new Jasca(jascaConfiguration);
		jasca.start();
	}
	
	private JascaConfiguration jascaConfiguration = null;
	
	public Jasca(JascaConfiguration jascaConfiguration) {
		this.jascaConfiguration = jascaConfiguration;
	}
	
	public void start() {
		try {
			ViolationResult violationResult = ViolationResult.getInstance();
			violationResult.create();
			
			List<Analyzer> analyzerList = getEnableAnalyzer();
			for (Analyzer analyzer : analyzerList) {
				analyzer.execute();
			}
			
			runFindbugs();
			runPmd();
			
			ReportBuilder.build(
					violationResult,
					new JascaConverter(),
					jascaConfiguration.createFormatter(),
					jascaConfiguration.getTarget(),
					jascaConfiguration.getOutput());
			
			violationResult.clear();
			
			log.info("분석 종료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<Analyzer> getEnableAnalyzer() {
		for (AnalyzerEngine analyzerEngine : jascaConfiguration.getEnableAnalyzerEngine()) {
			
		}
		
		return null;
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

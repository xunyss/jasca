package com.tyn.jasca;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.engine.ExcelFormatter;
import com.tyn.jasca.engine.HtmlFormatter;
import com.tyn.jasca.engine.XMLFormatter;

/**
 * 
 * @author S.J.H.
 */
public class JascaConfiguration {
	
	/**
	 * default..
	 */
	private AnalyzerEngine[] enableAnalyzerEngine = {
			AnalyzerEngine.FINDBUGS,
			AnalyzerEngine.PMD
	};
	
	private Severity severity;
	private String format;
	private String output;
	private String target;
	
	
	public AnalyzerEngine[] getEnableAnalyzerEngine() {
		return enableAnalyzerEngine;
	}

	public void setEnableAnalyzerEngine(AnalyzerEngine[] enableAnalyzerEngine) {
		this.enableAnalyzerEngine = enableAnalyzerEngine;
	}
	
	
	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 
	 * @param config
	 * @param command
	 * @throws ParseException
	 */
	public static void setup(JascaConfiguration config, CommandLine command) throws ParseException {
		String level = command.getOptionValue("l", "medium");
		String format = command.getOptionValue("f", "html");
		String output = command.getOptionValue("o");
		String[] target = command.getArgs();
		
		/*
		 * validation check
		 */
		if (!("high".equals(level) || "medium".equals(level) || "low".equals(level))) {
			throw new ParseException("분석 심각도 레벨 입력 오류");
		}
		if (!("html".equals(format) || "xml".equals(format) || "xls".equals(format))) {
			throw new ParseException("레포트 포맷 입력 오류");
		}
		if (target.length != 1) {
			throw new ParseException("분석대상 입력 오류");
		}
		
		config.severity = Severity.valueOf(level.toUpperCase());
		config.format = format;
		config.output = output;
		config.target = target[0];
	}
	
	/**
	 * 
	 * @return
	 */
	public Formatter createFormatter() {
		if ("html".equals(format)) {
			return new HtmlFormatter();
		}
		else if ("xml".equals(format)) {
			return new XMLFormatter();
		}
		else if ("xls".equals(format)) {
			return new ExcelFormatter();
		}
		
		return null;
	}
}

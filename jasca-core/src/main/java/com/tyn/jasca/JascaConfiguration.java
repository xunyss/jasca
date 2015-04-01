package com.tyn.jasca;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.common.Utils;
import com.tyn.jasca.formatters.ExcelFormatter;
import com.tyn.jasca.formatters.HtmlFormatter;
import com.tyn.jasca.formatters.XmlFormatter;

/**
 * 
 * @author S.J.H.
 */
public class JascaConfiguration {
	
	/**
	 * 분석시 사용할 분석엔진
	 * 
	 * @ TODO 외부 프로퍼티 설정
	 */
	private Map<AnalyzerEngine, Boolean> enableEngines = new HashMap<AnalyzerEngine, Boolean>(); {
		enableEngines.put(AnalyzerEngine.FINDBUGS,	true);
		enableEngines.put(AnalyzerEngine.PMD,		true);
	}
	
	private boolean debug;
	private Severity severity;
	private boolean progress;
	private String format;
	private String output;
	private String target;
	
	private ProgressCallback progressCallback = null;
	
	
	public Map<AnalyzerEngine, Boolean> getEnableEngines() {
		return enableEngines;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public Severity getSeverity() {
		return severity;
	}

	public boolean isProgress() {
		return progress;
	}

	public String getFormat() {
		return format;
	}

	public String getOutput() {
		return output;
	}

	public String getTarget() {
		return target;
	}
	
	
	public ProgressCallback getProgressCallback() {
		return progressCallback;
	}
	
	
	/**
	 * 
	 * @param config
	 * @param command
	 * @throws ParseException
	 */
	public static void setup(JascaConfiguration config, CommandLine command) throws ParseException {
		boolean debug		= command.hasOption("verbose");
		boolean progress	= command.hasOption("progress");
		String level		= command.getOptionValue("level", "medium");
		String format		= command.getOptionValue("format", "html");
		String output		= command.getOptionValue("output");
		String[] target		= command.getArgs();
		
		/*
		 * validation check
		 */
		if (!("high".equals(level) || "medium".equals(level) || "low".equals(level))) {
			throw new ParseException("분석 심각도 레벨 입력 오류");
		}
		if (!("html".equals(format) || "xls".equals(format) || "xlsx".equals(format) || "xml".equals(format))) {
			throw new ParseException("레포트 포맷 입력 오류");
		}
		if (Utils.isEmpty(output) && ("html".equals(format) || "xls".equals(format) || "xlsx".equals(format))) {
			throw new ParseException("결과저장 파일명 미입력");
		}
		if (target.length != 1) {
			throw new ParseException("분석대상 입력 오류");
		}
		
		
		/*
		 * configuration setup
		 */
		config.debug	= debug;
		config.progress	= progress;
		config.severity	= Severity.valueOf(level.toUpperCase());
		config.format	= format;
		config.output	= output;
		config.target	= target[0];
		if (config.progress) {
			config.progressCallback = new DefaultProgress();
		}
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
			return new XmlFormatter();
		}
		else if ("xls".equals(format) || "xlsx".equals(format)) {
			return new ExcelFormatter();
		}
		
		return null;
	}
}

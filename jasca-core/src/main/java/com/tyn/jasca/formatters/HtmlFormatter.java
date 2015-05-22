package com.tyn.jasca.formatters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.JascaException;
import com.tyn.jasca.Severity;
import com.tyn.jasca.Summary;
import com.tyn.jasca.Summary.CategoryCounter;
import com.tyn.jasca.SummaryFormatter;
import com.tyn.jasca.Violation;
import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.common.Utils;
import com.tyn.jasca.rules.Pattern;
import com.tyn.jasca.rules.Rule;

/**
 * 
 * @author S.J.H.
 */
public class HtmlFormatter implements SummaryFormatter {
	
	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(ExcelFormatter.class);
	
	private static final String STYLE_DIR = "jasca-css";
	private static final String DEFAULT_STYLE = "default";
	private static final Map<String, String[]> STYLES = new HashMap<String, String[]>();
	static {
		STYLES.put(DEFAULT_STYLE, new String[] { "default.css", "detail.png", "eg-f.png", "eg-p.png" });
	}
	
	private String style = null;
	
	private String input = null;
	private String output = null;
	private Writer writer = null;
	
	private int count = 0;
	
	public void wr(String string) throws IOException {
		writer.write(string + Utils.CRLF);
	}
	
	public HtmlFormatter() {
		this(DEFAULT_STYLE);
	}
	
	public HtmlFormatter(String style) {
		this.style = style;
	}
	
	@Override
	public void setInput(String input) {
		this.input = input;
	}

	@Override
	public void setOutput(String output) {
		this.output = output;
	}
	
	@Override
	public void start() {
		if (Utils.isEmpty(output)) {
			throw new JascaException("분석 결과를 저장할 파일명이 입력되지 않음");
		}
		
		try {
			writer = new BufferedWriter(new FileWriter(output));
			
			String targetDir = Utils.getDirName(output) + "/" + Utils.getFileNameExcludeExt(output);
			File saveDir = new File(targetDir);
			if (!saveDir.isDirectory()) {
				saveDir.mkdir();
			}
			
			String styleDir = STYLE_DIR + "/" + style;
			Utils.resourcesToFiles(styleDir, STYLES.get(style), targetDir);
		}
		catch (Exception e) {
			log.error("", e);
		}
	}
	
	@Override
	public void writeDocumentHead() throws IOException {
		wr("<!DOCTYPE html>");
		wr("<html>");
		wr("	<head>");
		wr("		<title>Jasca Report</title>");
		wr("		<link rel='stylesheet' type='text/css' href='" + Utils.getFileNameExcludeExt(output) + "/" + style + ".css' />");
		wr("	</head>");
		wr("	<body>");
		wr("		<h3>취약점 진단 REPORT</h3>");
		wr("		<p class=smr>");
		wr("			<span class=prj>Project : " + Utils.getSlashedPath(input) + "</span>");
		wr("			<span class=adt>Date : " + Utils.getCurrDatetime() + "</span>");
		wr("		</p>");
	}
	
	@Override
	public void writeSummary(Summary summary) throws IOException {
		
		/*
		 * 심각도별 요약
		 */
		int severityCount = Severity.values().length;
		wr("		<table class=sm1>");
		wr("			<thead>");
		wr("				<tr>");
		wr("					<td>합계</td>");
		for (int ordinal = 0; ordinal < severityCount; ordinal++) {
			wr("					<td>" + Severity.values()[ordinal].getText() + "</td>");
		}
		wr("				</tr>");
		wr("			</thead>");
		wr("			<tbody>");
		wr("				<tr>");
		wr("					<td>" + summary.getViolationCount() + "</td>");
		for (int ordinal = 0; ordinal < severityCount; ordinal++) {
			wr("					<td>" + summary.getSeverityCounts()[ordinal] + "</td>");
		}
		wr("				</tr>");
		wr("			</tbody>");
		wr("		</table>");
		wr("		<br/>");
		
		
		/*
		 * 카테고리별 요약
		 */
		wr("		<table class=sm2>");
		wr("			<thead>");
		wr("				<tr>");
		wr("					<td>보안약점 유형</td>");
		wr("					<td>탐지 건수</td>");
		wr("				</tr>");
		wr("			</thead>");
		wr("			<tbody>");
		
		Iterator<CategoryCounter> itr = summary.getCategoryCounterList().iterator();
		while (itr.hasNext()) {
			CategoryCounter categoryCounter = itr.next();
			wr("				<tr>");
			wr("					<td>" + categoryCounter.getCategory().getName() + "</td>");
			wr("					<td>" + categoryCounter.getCount() + "</td>");
			wr("				</tr>");
		}
		
		wr("			</tbody>");
		wr("		</table>");
		wr("		<br/>");
	}
	
	@Override
	public void writeViolationHead() throws IOException {
		wr("		<table class=lst>");
		wr("			<thead>");
		wr("				<tr>");
		wr("					<td rowspan=2 width=40>#</td>");
		wr("					<td rowspan=2 width=54>심각도</td>");
		wr("					<td>보안약점</td>");
		wr("					<td>파일명</td>");
		wr("				</tr>");
		wr("				<tr>");
		wr("					<td colspan=2>메시지</td>");
		wr("				</tr>");
		wr("			</thead>");
		wr("			<tbody>");
	}
	
	@Override
	public void writeViolationBody(Violation violation) throws IOException {
		Pattern pattern = violation.getPattern();
		Rule rule = violation.getRule();
		
		String sv = String.valueOf(rule.getSeverity().getValue());
		String eg = pattern.getAnalyzerEngine().equals(AnalyzerEngine.FINDBUGS) ? "f" : "p";
		String deatilUrl = Utils.isEmpty(rule.getCwe()) ? "#" : "http://cwe.mitre.org/data/definitions/" + rule.getCwe() + ".html";
		
		wr("<tr sv=" + sv + ">");
		wr("	<td rowspan=2>" + (++count) + "</td>");
		wr("	<td rowspan=2>" + rule.getSeverity().getText() + "</td>");
		wr("	<td eg=" + eg + ">" + rule.getName() + "</td>");
		wr("	<td>" + violation.getFilename() + " [" + violation.getBeginline() + "]</td>");
		wr("</tr>");
		wr("<tr sv=" + sv + ">");
		wr("	<td colspan=2>");
		wr(violation.getMessage());
		wr("<a href='" + deatilUrl + "' target=jasca><span class=dtl>&nbsp;</span></a>");
		wr("	</td>");
		wr("</tr>");
	}
	
	@Override
	public void writeViolationTail() throws IOException {
		wr("			</tbody>");
		wr("		</table>");
		wr("		<br/>");
	}
	
	@Override
	public void writeDocumentTail() throws IOException {
		wr("	</body>");
		wr("</html>");
	}
	
	@Override
	public void finish() {
		try {
			writer.flush();
		}
		catch (IOException e) {
			throw new IllegalStateException(e);
		}
		finally {
			IOUtils.closeQuietly(writer);
		}
	}
}

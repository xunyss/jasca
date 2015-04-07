package com.tyn.jasca.formatters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.JascaException;
import com.tyn.jasca.RulePattern;
import com.tyn.jasca.Severity;
import com.tyn.jasca.Summary;
import com.tyn.jasca.Summary.TypeCounter;
import com.tyn.jasca.SummaryFormatter;
import com.tyn.jasca.Violation;
import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.common.Utils;

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
			wr("					<td>" + summary.getSeveritySummary()[ordinal] + "</td>");
		}
		wr("				</tr>");
		wr("			</tbody>");
		wr("		</table>");
		wr("		<br/>");
		
//		List<RulePatternCounter> rulePatternSummary = summary.getRulePatternSummary();
//		int rulePatternSummaryLength = rulePatternSummary.size();
//		
//		wr("		<table class=sm2>");
//		wr("			<thead>");
//		wr("				<tr>");
//		wr("					<td width=40%>유형</td>");
//		wr("					<td width=20%>심각도</td>");
//		wr("					<td width=20%>계</td>");
//		wr("					<td width=20%>기타</td>");
//		wr("				</tr>");
//		wr("			</thead>");
//		wr("			<tbody>");
//		for (int idx = 0; idx < rulePatternSummaryLength; idx++) {
//			RulePattern rulePattern = rulePatternSummary.get(idx).getRulePattern();
//			String eg = rulePattern.getAnalyzerEngine().equals(Analyzer.AnalyzerEngine.FINDBUGS) ? "f" : "p";
//			
//			wr("				<tr>");
//			wr("					<td eg=" + eg + ">" + rulePattern.getTypename() + "</td>");
//			wr("					<td>" + rulePattern.getSeverity().getText() + "</td>");
//			wr("					<td>" + rulePatternSummary.get(idx).getCount() + "</td>");
//			wr("					<td>" + "" + "</td>");
//			wr("				</tr>");
//		}
//		wr("			</tbody>");
//		wr("		</table>");
//		wr("		<br/>");
		
		
		summary.buildTypeSummary();
		List<TypeCounter> typeSummary = summary.getTypeSummary();
		int typeSummaryLength = typeSummary.size();
		
		wr("		<table class=sm2>");
		wr("			<thead>");
		wr("				<tr>");
		wr("					<td width=40%>유형</td>");
		wr("					<td width=20%>심각도</td>");
		wr("					<td width=20%>계</td>");
		wr("					<td width=20%>기타</td>");
		wr("				</tr>");
		wr("			</thead>");
		wr("			<tbody>");
		for (int idx = 0; idx < typeSummaryLength; idx++) {
			TypeCounter typeCounter = typeSummary.get(idx);
			String eg = "";
			for (int egidx = 0; egidx < typeCounter.getEngines().length; egidx++) {
				if (typeCounter.getEngines()[egidx]) {
					if (AnalyzerEngine.values()[egidx].equals(AnalyzerEngine.FINDBUGS)) {
						eg += "<span class=fon>&nbsp;</span>";
					}
					else if (AnalyzerEngine.values()[egidx].equals(AnalyzerEngine.PMD)) {
						eg += "<span class=pon>&nbsp;</span>";
					}
				}
				else {
					eg += "<span class=off>&nbsp;</span>";
				}
			}
			
			wr("				<tr>");
			wr("					<td>" + eg + typeCounter.getTypename() + "</td>");
			wr("					<td>" + typeCounter.getSeveiry() + "</td>");
			wr("					<td>" + typeCounter.getCount() + "</td>");
			wr("					<td>" + "" + "</td>");
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
		wr("					<td width=320>유형</td>");
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
		RulePattern rulePattern = violation.getRulePattern();
		
		String sv = String.valueOf(rulePattern.getSeverity().getValue());
		String eg = rulePattern.getAnalyzerEngine().equals(Analyzer.AnalyzerEngine.FINDBUGS) ? "f" : "p";
		
		wr("<tr sv=" + sv + ">");
		wr("	<td rowspan=2>" + (++count) + "</td>");
		wr("	<td rowspan=2>" + rulePattern.getSeverity().getText() + "</td>");
		wr("	<td eg=" + eg + ">" + rulePattern.getTypename() + "</td>");
		wr("	<td>" + violation.getFilename() + " [" + violation.getBeginline() + "]</td>");
		wr("</tr>");
		wr("<tr sv=" + sv + ">");
		wr("	<td colspan=2>");
		wr(violation.getMessage());
		wr("<a href=" + rulePattern.getLink() + "><span class=dtl>&nbsp;</span></a>");
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

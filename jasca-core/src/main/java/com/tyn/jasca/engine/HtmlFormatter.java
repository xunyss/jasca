package com.tyn.jasca.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.tyn.jasca.JascaException;
import com.tyn.jasca.Severity;
import com.tyn.jasca.SummaryFormatter;
import com.tyn.jasca.Violation;
import com.tyn.jasca.ViolationSummary;
import com.tyn.jasca.ViolationSummary.TypeCounter;
import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.common.Utils;

/**
 * 
 * @author S.J.H.
 */
public class HtmlFormatter implements SummaryFormatter {
	
	/**
	 * 
	 */
//	private static final Logger log = Jasca.getLogger();
	
	private static final String CRLF = "\r\n";
	
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
		writer.write(string + CRLF);
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
		
		try {
			writer = new BufferedWriter(new FileWriter(output));
		}
		catch (IOException ioe) {
			throw new JascaException("파일을 생성할 수 없음 : " + output, ioe);
		}
	}
	
	@Override
	public void start() {
		try {
			String targetDir = Utils.getDirName(output) + "/" + Utils.getFileNameExcludeExt(output);
			
			File saveDir = new File(targetDir);
			if (!saveDir.isDirectory()) {
				saveDir.mkdir();
			}
			
			String styleDir = STYLE_DIR + "/" + style;
			Utils.resourcesToFiles(styleDir, STYLES.get(style), targetDir);
		}
		catch (Exception e) {
			e.printStackTrace();
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
	public void writeSummary(ViolationSummary violationSummary) throws IOException {
		
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
		wr("					<td>" + violationSummary.getViolationCount() + "</td>");
		for (int ordinal = 0; ordinal < severityCount; ordinal++) {
			wr("					<td>" + violationSummary.getSeveritySummary()[ordinal] + "</td>");
		}
		wr("				</tr>");
		wr("			</tbody>");
		wr("		</table>");
		wr("		<br/>");
		
		List<TypeCounter> typeSummary = violationSummary.getTypeSummary();
		int typeSummaryLenth = typeSummary.size();
		
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
		for (int idx = 0; idx < typeSummaryLenth; idx++) {
			wr("				<tr>");
			wr("					<td>" + typeSummary.get(idx).getViolationSource().getType() + "</td>");
			wr("					<td>" + "HIGH|MEDIUM|LOW" + "</td>");
			wr("					<td>" + typeSummary.get(idx).getTypeCount() + "</td>");
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
		String sv = String.valueOf(violation.getSeverity().getValue());
		String eg = violation.getAnalyzer().equals(Analyzer.AnalyzerEngine.FINDBUGS) ? "f" : "p";
		
		wr("<tr sv=" + sv + ">");
		wr("	<td rowspan=2>" + (++count) + "</td>");
		wr("	<td rowspan=2>" + violation.getSeverity().getText() + "</td>");
		wr("	<td eg=" + eg + ">" + violation.getType() + "</td>");
		wr("	<td>" + violation.getFilename() + " [" + violation.getBeginline() + "]</td>");
		wr("</tr>");
		wr("<tr sv=" + sv + ">");
		wr("	<td colspan=2>");
		wr(violation.getMessage());
		wr("<a><span class=dtl>&nbsp;</span></a>");
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
			if (!writer.getClass().equals(OutputStreamWriter.class)) {
				IOUtils.closeQuietly(writer);
			}
		}
	}
}

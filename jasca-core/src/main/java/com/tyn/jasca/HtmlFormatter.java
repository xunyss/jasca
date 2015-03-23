package com.tyn.jasca;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.common.Utils;

/**
 * 
 * @author S.J.H.
 */
public class HtmlFormatter implements Formatter {
	
	private static final String CRLF = "\r\n";
	
	private static final String STYLE_DIR = "html-style";
	private static final Map<String, String[]> STYLES = new HashMap<String, String[]>();
	static {
		STYLES.put("jasca-default", new String[] { "jasca-default.css", "eg-f.png", "eg-p.png", "detail.png" });
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
		this("jasca-default");
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
	public void setWriter(Writer writer) {
		this.writer = writer;
	}
	
	@Override
	public void start() {
		try {
			String targetDir = Utils.getDirName(output) + "/" + style;
			
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
	public void writeHead() throws IOException {
		wr("<!DOCTYPE html>");
		wr("<html>");
		wr("	<head>");
		wr("		<title>Jasca Report</title>");
		wr("		<link rel='stylesheet' type='text/css' href='" + style + "/" + style + ".css' />");
		wr("	</head>");
		wr("	<body>");
		wr("		<h3>취약점 진단 REPORT</h3>");
		wr("		<p class=smr>");
		wr("			<span class=prj>Project : " + Utils.getSlashedPath(input) + "</span>");
		wr("			<span class=adt>Date : " + Utils.getCurrDatetime() + "</span>");
		wr("		</p>");
		wr("		<table>");
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
	public void writeBody(Violation violation) throws IOException {
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
	public void writeTail() throws IOException {
		wr("			</tbody>");
		wr("		</table>");
		wr("		<br/>");
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

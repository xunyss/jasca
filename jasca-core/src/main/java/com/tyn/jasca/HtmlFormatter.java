package com.tyn.jasca;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author S.J.H.
 */
public class HtmlFormatter implements Formatter {
	
	private Writer writer = null;
	private int count = 0;
	
	public void wr(String s) throws IOException { writer.write(s + "\r\n"); }
	
	@Override
	public void setWriter(Writer writer) {
		this.writer = writer;
	}
	
	@Override
	public void start() {
		
	}
	
	@Override
	public void writeHead() throws IOException {
		wr("<html>");
		wr("	<head>");
		wr("		<title>jasca report</title>");
		wr("	</head>");
		wr("	<body>");
		wr("		<h3>취약점 진단 보고서</h3>");
		wr("		<table border=1>");
		wr("			<tr>");
		wr("				<td>순번</td>");
		wr("				<td>엔진</td>");
		wr("				<td>심각도</td>");
		wr("				<td>유형</td>");
		wr("				<td>파일명</td>");
		wr("				<td>줄번호</td>");
		wr("				<td>메시지</td>");
		wr("			</tr>");
	}
	
	@Override
	public void writeBody(Violation violation) throws IOException {
		wr("<tr>");
		wr("	<td>" + (++count) + "</td>");
		wr("	<td>" + (violation.getAnalyzer()) + "</td>");
		wr("	<td>" + (violation.getSeverity()) + "</td>");
		wr("	<td>" + (violation.getType()) + "</td>");
		wr("	<td>" + (violation.getFilename()) + "</td>");
		wr("	<td>" + (violation.getBeginline()) + "</td>");
		wr("	<td>" + (violation.getMessage()) + "</td>");
		wr("</tr>");
	}
	
	@Override
	public void writeTail() throws IOException {
		wr("		</table>");
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

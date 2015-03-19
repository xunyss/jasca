package com.tyn.jasca;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author S.J.H.
 */
public class Htmler {
	
	static BufferedWriter writer = null;
	static List<Violation> list = null;
	
	public static void wr(String s) throws Exception { writer.write(s + "\r\n"); }
	
	public static void make() throws Exception {
		start();
		head();
		body();
		tail();
		finish();
	}
	
	private static void start() throws Exception {
		File file = new File("D:/xdev/git/jasca/jasca-core/target/htmler.html");
		writer = new BufferedWriter(new FileWriter(file));
		
		list = ViolationResult.getInstance().getViolations();
		Collections.sort(list, new Comparator<Violation>() {
			@Override
			public int compare(Violation o1, Violation o2) {
				// 1. severity
				int severity = o1.getSeverity() - o2.getSeverity();
				if (severity != 0) {
					return severity;
				}
				// 2. TODO: filename
				else {
					return severity;
				}
			}
		});
	}
	
	private static void head() throws Exception {
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
	
	private static void body() throws Exception {
		int no = 0;
		for (Violation ov : list) {
			Violation v = Sec47.conv(ov);
			
			wr("<tr>");
			wr("	<td>" + (++no) + "</td>");
			wr("	<td>" + (v.getEngine()) + "</td>");
			wr("	<td>" + (v.getSeverity()) + "</td>");
			wr("	<td>" + (v.getType()) + "</td>");
			wr("	<td>" + (v.getFilename()) + "</td>");
			wr("	<td>" + (v.getLine()) + "</td>");
			wr("	<td>" + (v.getMessage()) + "</td>");
			wr("</tr>");
		}
	}
	
	private static void tail() throws Exception {
		wr("		</table>");
		wr("	</body>");
		wr("</html>");
	}
	
	private static void finish() throws Exception {
		writer.close();
	}
}

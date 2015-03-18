package com.tyn.jasca.analyzer.pmd;

/**
 * 
 * @author S.J.H.
 */
public class PmdConstant {

	/**
	 * 
	 */
	public enum ReportFormat {
		TEXT		("text"),
		TEXTPAD		("textpad"),
		TEXTCOLOR	("textcolor"),
		EMACS		("emacs"),
		CSV			("csv"),
		XML			("xml"),
		XSLT		("xslt"),
		HTML		("html"),
		YAHTML		("yahtml"),
		VBHTML		("vbhtml"),
		SUMMARYHTML	("summaryhtml"),
		
		CUSTOM		("custom");
		
		private String reportformat;
		
		private ReportFormat(String reportformat) {
			this.reportformat = reportformat;
		}
		
		public String getReportFormat() {
			return reportformat;
		}
	}
}

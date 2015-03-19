package com.tyn.jasca.analyzer.pmd;

/**
 * 
 * @author S.J.H.
 */
public class PmdConstant {

	/**
	 * 
	 */
	public enum RenderFormat {
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
		
		private RenderFormat(String reportformat) {
			this.reportformat = reportformat;
		}
		
		public String getReportFormat() {
			return reportformat;
		}
	}
}

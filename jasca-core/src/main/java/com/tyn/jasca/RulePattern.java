package com.tyn.jasca;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;

/**
 * 
 * @author S.J.H.
 */
public class RulePattern implements Cloneable {
	
	private AnalyzerEngine analyzerEngine;		/** key */
	private String typename;					/** key */
	private boolean isRegistered;
	
	private String description;
	private String detail;
	private String link;
	private Severity severity;
	private String category;
	private String extra;
	
	public RulePattern() {
		
	}
	
	public RulePattern(AnalyzerEngine analyzerEngine, String typename) {
		setAnalyzerEngine(analyzerEngine);
		setTypename(typename);
	}
	
	public AnalyzerEngine getAnalyzerEngine() {
		return analyzerEngine;
	}
	
	public void setAnalyzerEngine(AnalyzerEngine analyzerEngine) {
		this.analyzerEngine = analyzerEngine;
	}
	
	public String getTypename() {
		return typename;
	}
	
	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Severity getSeverity() {
		return severity;
	}
	
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getExtra() {
		return extra;
	}
	
	public void setExtra(String extra) {
		this.extra = extra;
	}

	
	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return analyzerEngine.ordinal() +
				typename.hashCode();
	}
	
	/**
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof RulePattern) {
			RulePattern rulePattern = (RulePattern) object;
			return
					rulePattern.analyzerEngine == analyzerEngine &&
					rulePattern.typename.equals(typename);
		}
		return false;
	}
	
	/**
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RulePattern clone() {
		try {
			return (RulePattern) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			throw new JascaException(cnse);
		}
	}
}

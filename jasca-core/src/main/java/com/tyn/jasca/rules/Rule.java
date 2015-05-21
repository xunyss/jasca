package com.tyn.jasca.rules;

import java.util.List;
import java.util.Set;

import com.tyn.jasca.JascaException;
import com.tyn.jasca.Severity;

/**
 * 
 * @author S.J.H.
 */
public class Rule implements Cloneable, Comparable<Rule> {
	
	private String			id;
	private Category		category;
	private String			name;
	private String			cwe;
	private Severity		severity;
	private String			description;
	private String			detail;
	private List<String>	links;
	private Set<Pattern>	patterns;
	private int				order = Integer.MAX_VALUE;
	
	/** default */ Rule(String id) {
		setId(id);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCwe() {
		return cwe;
	}
	
	public void setCwe(String cwe) {
		this.cwe = cwe;
	}
	
	public Severity getSeverity() {
		return severity;
	}
	
	public void setSeverity(Severity severity) {
		this.severity = severity;
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
	
	public List<String> getLinks() {
		return links;
	}
	
	public void setLinks(List<String> links) {
		this.links = links;
	}
	
	public Set<Pattern> getPatterns() {
		return patterns;
	}
	
	public void setPatterns(Set<Pattern> patterns) {
		this.patterns = patterns;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
//	/**
//	 * 
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		return id.hashCode();
//	}
//	
//	/**
//	 * 
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object object) {
//		if (object == this) {
//			return true;
//		}
//		if (object instanceof Rule) {
//			Rule rule = (Rule) object;
//			return id.equals(rule.id);
//		}
//		return false;
//	}
	
	/**
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Rule clone() {
		try {
			return (Rule) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			throw new JascaException(cnse);
		}
	}
	
	/**
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Rule rule) {
		int severityOrder = severity.getValue() - rule.severity.getValue();
		int ruleOrder = order - rule.getOrder();
		
		return severityOrder != 0
				? severityOrder
				: (ruleOrder != 0 ? ruleOrder : id.compareTo(rule.id));
	}
}

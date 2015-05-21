package com.tyn.jasca.rules;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.util.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tyn.jasca.JascaException;
import com.tyn.jasca.Severity;
import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.common.DomUtils;

/**
 * 
 * @author S.J.H.
 */
public class RuleRepository {
	
	private static final String JASCA_RULES_XML = "jasca-rules.xml";
	
	private Map<String, Category> categories = null;
	private Map<Pattern, Rule> rules = null;
	
	/**
	 * singleton
	 */
	private static class Holder {
		private static final RuleRepository instance = new RuleRepository();
	}
	
	/**
	 * 
	 * @return
	 */
	public static RuleRepository getInstance() {
		return Holder.instance;
	}
	
	/**
	 * 
	 * @param pattern
	 * @return
	 */
	public Rule getRule(Pattern pattern) {
		Rule rule = rules.get(pattern);
		
		if (rule == null) {
			rule = new Rule(pattern.toString());
			rule.setCategory(Category.ETC);
			rule.setName(pattern.toString());
			rule.setSeverity(pattern.getSeverity());
			
			rules.put(pattern, rule);
		}
		
		return rule;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Category getCategory(String id) {
		Category category = categories.get(id);
		
		if (category == null) {
			category = new Category(id);
			category.setName(id);
			
			categories.put(id, category);
		}
		
		return category;
	}
	
	private RuleRepository() {
		categories = new HashMap<String, Category>();
		rules = new HashMap<Pattern, Rule>();
		
		loadDefaultRulesXML();
	}
	
	private void loadDefaultRulesXML() {
		InputStream inputStream = null;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(JASCA_RULES_XML);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document document = builder.parse(inputStream);
			Element rootNode = document.getDocumentElement();
			
			/*
			 * load categories
			 */
			loadCategories(rootNode.getElementsByTagName("categories").item(0));
			
			/*
			 * load rules
			 */
			loadRules(rootNode.getElementsByTagName("rules").item(0));
		}
		catch (Exception e) {
			throw new JascaException(JASCA_RULES_XML + "로딩 에러", e);
		}
		finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
	private void loadCategories(Node categoryNode) {
		NodeList categoryNodeList = ((Element) categoryNode).getElementsByTagName("category");
		int categoryNodeLen = categoryNodeList.getLength();
		
		for (int i = 0; i < categoryNodeLen; i++) {
			Element elCategory = (Element) categoryNodeList.item(i);
			if (elCategory.getNodeName().equals("category")) {
				String id			= elCategory.getAttribute("id");
				String name			= DomUtils.getElText(elCategory, "name");
				String cwe			= DomUtils.getElText(elCategory, "cwe");
				String description	= DomUtils.getElText(elCategory, "description");
				
				Category category = new Category(id);
				category.setName(name);
				category.setCwe(cwe);
				category.setDescription(description);
				category.setOrder(i);
				
				categories.put(id, category);
			}
		}
	}
	private void loadRules(Node ruleNode) {
		NodeList ruleNodeList = ((Element) ruleNode).getElementsByTagName("rule");
		int ruleNodeLen = ruleNodeList.getLength();
		
		for (int i = 0; i < ruleNodeLen; i++) {
			Element elRule = (Element) ruleNodeList.item(i);
			if (elRule.getNodeName().equals("rule")) {
				String id				= elRule.getAttribute("id");
				String category			= DomUtils.getElText(elRule, "category");
				String name				= DomUtils.getElText(elRule, "name");
				String cwe				= DomUtils.getElText(elRule, "cwe");
				String description		= DomUtils.getElText(elRule, "description");
				String detail			= DomUtils.getElText(elRule, "detail");
				String severity			= DomUtils.getElText(elRule, "severity");
				List<String> links		= DomUtils.getElTextList(elRule, "link");
				Set<Pattern> patterns	= loadRulePatterns(elRule.getElementsByTagName("pattern"));
				
				Rule rule = new Rule(id);
				rule.setCategory	(getCategory(category));
				rule.setName		(name);
				rule.setCwe			(cwe);
				rule.setDescription	(description);
				rule.setDetail		(detail);
				rule.setSeverity	(Severity.valueOf(severity.toUpperCase()));
				rule.setLinks		(links);
				rule.setPatterns	(patterns);
				rule.setOrder		(i);
				
				bindPatternRuleMap(patterns, rule);
			}
		}
	}
	private Set<Pattern> loadRulePatterns(NodeList patternNodeList) {
		Set<Pattern> patterns = new HashSet<Pattern>();
		
		if (patternNodeList != null) {
			int patternNodeLen = patternNodeList.getLength();
			for (int j = 0; j < patternNodeLen; j++) {
				Element elPattern = (Element) patternNodeList.item(j);
				String engine = elPattern.getAttribute("engine");
				String type = elPattern.getAttribute("type");
				
				Pattern pattern = Pattern.get(AnalyzerEngine.valueOf(engine.toUpperCase()), type);
				patterns.add(pattern);
			}
		}
		
		return patterns;
	}
	private void bindPatternRuleMap(Set<Pattern> patterns, Rule rule) {
		Iterator<Pattern> itr = patterns.iterator();
		while (itr.hasNext()) {
			rules.put(itr.next(), rule);
		}
	}
}

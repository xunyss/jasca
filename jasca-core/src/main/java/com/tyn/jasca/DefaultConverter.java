package com.tyn.jasca;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.tyn.jasca.JascaException;
import com.tyn.jasca.RulePattern;
import com.tyn.jasca.RulePatternCollection;
import com.tyn.jasca.Severity;
import com.tyn.jasca.Violation;
import com.tyn.jasca.ViolationConverter;
import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.common.Utils;

/**
 * 
 * @author S.J.H.
 */
public class DefaultConverter implements ViolationConverter {
	
	/**
	 * 
	 */
//	private static final Logger log = LoggerFactory.getLogger(JascaConverter.class);
	
	private static final String CONVERSION_DEFINE_XML = "jasca-rulepattern.xml";
	
	private String input = null;
//	private String output = null;
	
	private boolean isInitialize = false;
	private Map<RulePattern, RulePattern> conversionMap = new HashMap<RulePattern, RulePattern>();
	private Set<RulePattern> ignoreSet = new HashSet<RulePattern>();
	
	@Override
	public void setInput(String input) {
		this.input = input + (input.endsWith("/") ? "" : "/");
	}
	
	@Override
	public void setOutput(String output) {
//		this.output = output;
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.ViolationConverter#convert(com.tyn.jasca.Violation)
	 */
	@Override
	public Violation convert(Violation violation) {
		
		if (!isInitialize) {
			loadConversionXML();
		}
		
		RulePattern rulePattern = violation.getRulePattern();
		
		if (ignoreSet.contains(rulePattern)) {
			return null;
		}
		
		RulePattern convertedRulePattern = conversionMap.get(violation.getRulePattern());
		if (convertedRulePattern != null) {
			violation.setRulePattern(convertedRulePattern);
			rulePattern = convertedRulePattern;
		}
		
		
		/*
		 * filename change
		 * TODO 실제 파일조사를 통한 정확한 변환
		 */
		if (AnalyzerEngine.PMD.equals(rulePattern.getAnalyzerEngine())) {
			String filepath = violation.getFilename();
			filepath = filepath.replace('\\', '/').substring(input.length());
			if (filepath.endsWith(".java")) {
				filepath = filepath.substring("/src".length());
			}
			violation.setFilename(filepath);
		}
		
		return violation;
	}
	
	private void loadConversionXML() {
		InputStream inputStream = null;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(CONVERSION_DEFINE_XML);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document document = builder.parse(inputStream);
			Element rootNode = document.getDocumentElement();
			NodeList nodeList = rootNode.getElementsByTagName("rulepattern");
			
			int len = nodeList.getLength();
			for (int idx = 0; idx < len; idx++) {
				Element node = (Element) nodeList.item(idx);
				
				/**
				 * originalPattern 은 isRegistered == true 이어야 함
				 */
				RulePattern originalPattern = RulePatternCollection.getInstance().get(
						AnalyzerEngine.valueOf(node.getAttribute("engine").toUpperCase()),
						node.getAttribute("name"));
				
				if (Boolean.valueOf(node.getAttribute("ignore"))) {
					ignoreSet.add(originalPattern);
					continue;
				}
				
				String typename = getElText(node, "typename");
				String description = getElText(node, "description");
				String detail = getElText(node, "detail");
				String link = getElText(node, "link");
				String severity = getElText(node, "severity");
				
				RulePattern convertedPattern = originalPattern.clone();
				if (!Utils.isEmpty(typename)) {
					convertedPattern.setTypename(getElText(node, "typename"));
				}
				if (!Utils.isEmpty(description)) {
					convertedPattern.setDescription(getElText(node, "description"));
				}
				if (!Utils.isEmpty(detail)) {
					convertedPattern.setDetail(getElText(node, "detail"));
				}
				if (!Utils.isEmpty(link)) {
					convertedPattern.setLink(getElText(node, "link"));
				}
				if (!Utils.isEmpty(severity)) {
					convertedPattern.setSeverity(Severity.valueOf(severity.toUpperCase()));
				}
				
				/**
				 * RulePatternCollection 에는 등록하지 않음.
				 */
					
				conversionMap.put(originalPattern, convertedPattern);
			}
			
			isInitialize = true;
		}
		catch (Exception e) {
			throw new JascaException(CONVERSION_DEFINE_XML + "로딩 에러", e);
		}
		finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	private static String getElText(Element node, String elName) {
		NodeList ellist = node.getElementsByTagName(elName);
		if (ellist != null && ellist.getLength() > 0) {
			return ellist.item(0).getTextContent();
		}
		return null;
	}
}

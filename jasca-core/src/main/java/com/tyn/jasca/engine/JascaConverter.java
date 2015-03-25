package com.tyn.jasca.engine;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.tyn.jasca.JascaException;
import com.tyn.jasca.Severity;
import com.tyn.jasca.Violation;
import com.tyn.jasca.ViolationConverter;
import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.common.Utils;

/**
 * 
 * @author S.J.H.
 */
public class JascaConverter implements ViolationConverter {
	
	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(JascaConverter.class);
	
	private static final String CONVERSION_DEFINE_XML = "jasca-violations.xml";
	
	private String input = null;
//	private String output = null;
	
	private boolean isInitialize = false;
	private Map<ViolationSource, Violation> conversionmap = new HashMap<ViolationSource, Violation>();
	
	@Override
	public void setInput(String input) {
		this.input = input + (input.endsWith("/") ? "" : "/");
	}
	
	@Override
	public void setOutput(String output) {
//		this.output = output;
	}
	
	@Override
	public Violation convert(Violation violation) {
		if (!isInitialize) {
			loadConversionXML();
		}
		
		Violation convViolation = conversionmap.get(new ViolationSource(violation.getAnalyzer(), violation.getType()));
		if (convViolation != null) {
			String type = convViolation.getType();
			String message = convViolation.getMessage();
			Severity severity = convViolation.getSeverity();
			
			if (!Utils.isEmpty(type)) {
				violation.setType(type);
			}
			if (!Utils.isEmpty(message)) {
				violation.setMessage(message);
			}
			if (severity != null) {
				violation.setSeverity(severity);
			}
		}
		
		/*
		 * filename change
		 */
		if (AnalyzerEngine.PMD.equals(violation.getAnalyzer())) {
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
			Element rootnode = document.getDocumentElement();
			NodeList vnodes = rootnode.getElementsByTagName("violation");
			
			int len = vnodes.getLength();
			for (int idx = 0; idx < len; idx++) {
				Element vnode = (Element) vnodes.item(idx);
				
				ViolationSource vsrc =
						new ViolationSource(
								AnalyzerEngine.valueOf(vnode.getAttribute("engine").toUpperCase()),
								vnode.getAttribute("type"));
				
				Violation vconv = new Violation();
				vconv.setType(getElText(vnode, "type"));
				vconv.setMessage(getElText(vnode, "message"));
				String severity = getElText(vnode, "severity");
				if (!Utils.isEmpty(severity)) {
					vconv.setSeverity(Severity.valueOf(severity.toUpperCase()));
				}
				
				conversionmap.put(vsrc, vconv);
			}
			
			isInitialize = true;
		}
		catch (Exception e) {
			log.error("XML 파싱중 에러 발생", e);
			throw new JascaException();
		}
		finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	private String getElText(Element node, String elName) {
		NodeList ellist = node.getElementsByTagName(elName);
		if (ellist != null && ellist.getLength() > 0) {
			return ellist.item(0).getTextContent();
		}
		return null;
	}
	
	static class ViolationSource {
		AnalyzerEngine analyzer;
		String type;
		
		ViolationSource(AnalyzerEngine analyzer, String type) {
			this.analyzer = analyzer;
			this.type = type;
		}
		
		@Override
		public boolean equals(Object object) {
			if (object == this) {
				return true;
			}
			if (object instanceof ViolationSource) {
				ViolationSource vsrc = (ViolationSource) object;
				return
						vsrc.analyzer == analyzer &&
						vsrc.type.equals(type);
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return type.hashCode() & analyzer.ordinal();
		}
	}
}

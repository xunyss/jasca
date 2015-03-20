package com.tyn.jasca;

import java.io.IOException;
import java.io.OutputStreamWriter;

import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RulePriority;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.renderers.AbstractRenderer;
import net.sourceforge.pmd.util.datasource.DataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;

/**
 * 
 * @author S.J.H.
 */
public class JascaRenderer extends AbstractRenderer {
	
	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(JascaRenderer.class);
	
	/**
	 * 
	 */
	public JascaRenderer() {
		super("", "");
	}
	
	/**
	 * 
	 */
	public JascaRenderer(String name, String description) {
		super(name, description);
	}
	
	/**
	 * 
	 * @see net.sourceforge.pmd.renderers.Renderer#defaultFileExtension()
	 */
	@Override
	public String defaultFileExtension() {
		return null;
	}

	/**
	 * 
	 * @see net.sourceforge.pmd.renderers.Renderer#start()
	 */
	@Override
	public void start() throws IOException {
		
	}
	
	/**
	 * 
	 * @see net.sourceforge.pmd.renderers.Renderer#startFileAnalysis(net.sourceforge.pmd.util.datasource.DataSource)
	 */
	@Override
	public void startFileAnalysis(DataSource dataSource) {
		
	}
	
	/**
	 * 
	 * @see net.sourceforge.pmd.renderers.Renderer#renderFileReport(net.sourceforge.pmd.Report)
	 */
	@Override
	public void renderFileReport(Report report) throws IOException {
		for (RuleViolation ruleViolation : report) {
			Violation violation = new Violation();
			violation.setAnalyzer(AnalyzerEngine.PMD);
			violation.setFilename(ruleViolation.getFilename());
			violation.setBeginline(ruleViolation.getBeginLine());
			violation.setEndline(ruleViolation.getEndLine());
			violation.setMessage(ruleViolation.getDescription());
			violation.setSeverity(toSeverity(ruleViolation.getRule().getPriority()));
			violation.setType(ruleViolation.getRule().getName());
			
			ViolationResult.getInstance()
				.add(violation);
		}
	}
	
	/**
	 * 
	 * @see net.sourceforge.pmd.renderers.Renderer#end()
	 */
	@Override
	public void end() throws IOException {
		
	}
	
	/**
	 * 
	 */
	@Override
	public void flush() {
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
	
	/**
	 * 
	 * @param rulePriority
	 * @return
	 */
	private Severity toSeverity(RulePriority rulePriority) {
		switch (rulePriority) {
		case HIGH:
		case MEDIUM_HIGH:
			return Severity.HIGH;
		
		case MEDIUM:
			return Severity.MEDIUM;
		
		case MEDIUM_LOW:
		case LOW:
			return Severity.LOW;
		
		default:
			throw new IllegalArgumentException();
		}
	}
}

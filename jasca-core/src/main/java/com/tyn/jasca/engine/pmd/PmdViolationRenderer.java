package com.tyn.jasca.engine.pmd;

import java.io.IOException;
import java.io.OutputStreamWriter;

import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.renderers.AbstractRenderer;
import net.sourceforge.pmd.util.datasource.DataSource;

import org.apache.commons.io.IOUtils;

import com.tyn.jasca.Results;
import com.tyn.jasca.Violation;
import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.engine.SeverityLevel;
import com.tyn.jasca.rules.Pattern;
import com.tyn.jasca.rules.Rule;
import com.tyn.jasca.rules.RuleRepository;

/**
 * 
 * @author S.J.H.
 */
public class PmdViolationRenderer extends AbstractRenderer {
	
	/**
	 * 
	 */
//	private static final Logger log = LoggerFactory.getLogger(JascaRenderer.class);
	
	/**
	 * 
	 */
	public PmdViolationRenderer() {
		super("", "");
	}
	
	/**
	 * 
	 */
	public PmdViolationRenderer(String name, String description) {
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
			// Pattern
			Pattern pattern = Pattern.get(
					AnalyzerEngine.PMD,
					ruleViolation.getRule().getName(),
					SeverityLevel.getSeverity(ruleViolation.getRule().getPriority()));
			
			// Rule
			RuleRepository ruleCollection = RuleRepository.getInstance();
			Rule rule = ruleCollection.getRule(pattern);
			
			// Violation
			Violation violation = new Violation();
			violation.setPattern(pattern);
			violation.setRule(rule);
			
			violation.setFilename(ruleViolation.getFilename());
			violation.setBeginline(ruleViolation.getBeginLine());
			violation.setEndline(ruleViolation.getEndLine());
			violation.setMessage(ruleViolation.getDescription());
			
			Results.getInstance()
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
}

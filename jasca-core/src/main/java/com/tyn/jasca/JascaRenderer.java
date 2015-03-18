package com.tyn.jasca;

import java.io.IOException;

import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.renderers.AbstractRenderer;
import net.sourceforge.pmd.util.datasource.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.analyzer.pmd.PmdProgress;

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
		super("jascaRenderer", "jascaRenderer");
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
			violation.setEngine("P");
			violation.setFilename(ruleViolation.getFilename());
			violation.setLine(ruleViolation.getBeginLine());
			violation.setMessage(ruleViolation.getDescription());
			violation.setSeverity(ruleViolation.getRule().getPriority().getPriority());
			violation.setType(ruleViolation.getRule().getName());
			
			MyListener.add(violation);
		}
	}
	
	/**
	 * 
	 * @see net.sourceforge.pmd.renderers.Renderer#end()
	 */
	@Override
	public void end() throws IOException {
		
	}
}

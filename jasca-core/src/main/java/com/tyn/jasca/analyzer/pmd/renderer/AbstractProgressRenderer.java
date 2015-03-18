package com.tyn.jasca.analyzer.pmd.renderer;

import net.sourceforge.pmd.renderers.AbstractRenderer;
import net.sourceforge.pmd.util.datasource.DataSource;

import com.tyn.jasca.analyzer.pmd.PmdProgress;

/**
 * 
 * @author S.J.H.
 */
public abstract class AbstractProgressRenderer extends AbstractRenderer implements PmdProgress {
	
	private int totalCount = 0;
	private int passCount = 0;
	
	/**
	 * 
	 * @param name
	 * @param description
	 */
	public AbstractProgressRenderer(String name, String description) {
		super(name, description);
	}
	
	/**
	 * 
	 * @see net.sourceforge.pmd.renderers.Renderer#startFileAnalysis(net.sourceforge.pmd.util.datasource.DataSource)
	 */
	@Override
	public void startFileAnalysis(DataSource dataSource) {
		analyzeFile(++passCount);
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.analyzer.pmd.PmdProgress#startAnalyze(int)
	 */
	@Override
	public void startAnalyze(int totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.analyzer.pmd.PmdProgress#analyzeFile(int)
	 */
	@Override
	public void analyzeFile(int passCount) {
		doProgress(totalCount, passCount);
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.analyzer.pmd.PmdProgress#finishAnalyze()
	 */
	@Override
	public void finishAnalyze() {
		// do nothing..
	}
	
	
	/**
	 * 
	 * @param total
	 * @param pass
	 */
	abstract void doProgress(int total, int pass);
}

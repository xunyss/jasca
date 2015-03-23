package com.tyn.jasca.analyzer.pmd.renderer;

import net.sourceforge.pmd.PropertyDescriptor;
import net.sourceforge.pmd.lang.rule.properties.IntegerProperty;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.util.datasource.DataSource;

import com.tyn.jasca.analyzer.pmd.PmdProgress;


/**
 * 
 * @author S.J.H.
 */
public final class ProgressRenderer extends DelegatingRenderer {
	
	/**
	 * 
	 */
	private static final String TOTAL_COUNT_NAME = ProgressRenderer.class.getName() + ".TOTAL_COUNT_NAME";
	
	/**
	 * 
	 */
	public static final IntegerProperty TOTAL_COUNT_PROPERTY = new IntegerProperty(TOTAL_COUNT_NAME, TOTAL_COUNT_NAME, 0, 0, 0, 0f);
	
	
	/**
	 * 
	 */
	private PmdProgress progress = null;
	
	/**
	 * 
	 * @param renderer
	 * @param progress
	 */
	public ProgressRenderer(Renderer renderer, PmdProgress progress) {
		super(renderer);
		this.progress = progress;
	}
	
	
	/**
	 * 
	 */
	private int passCount = 0;
	
	/**
	 * 
	 * @see com.tyn.jasca.analyzer.pmd.renderer.DelegatingRenderer#setProperty(net.sourceforge.pmd.PropertyDescriptor, java.lang.Object)
	 */
	@Override
	public <T> void setProperty(PropertyDescriptor<T> propertyDescriptor, T value) {
		if (propertyDescriptor.name().equals(TOTAL_COUNT_NAME)) {
			
			/**
			 * 
			 */
			progress.startAnalyze((Integer) value);
		}
		
		if (isSameDelegator()) {
			getDelegate().setProperty(propertyDescriptor, value);
		}
	}
	
	@Override
	public void startFileAnalysis(DataSource dataSource) {
		
		/**
		 * 
		 */
		progress.analyzeFile(++passCount);
		
		getDelegate().startFileAnalysis(dataSource);
	}
}

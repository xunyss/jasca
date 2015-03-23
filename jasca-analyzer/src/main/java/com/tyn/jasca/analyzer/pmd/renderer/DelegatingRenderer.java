package com.tyn.jasca.analyzer.pmd.renderer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pmd.PropertyDescriptor;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.util.datasource.DataSource;

/**
 * 
 * @author S.J.H.
 */
public abstract class DelegatingRenderer implements Renderer {
	
	/**
	 * 
	 */
	private Renderer renderer = null;
	
	/**
	 * 
	 */
	public final void setRealRenderer(Renderer renderer) {
		if (this.renderer instanceof DelegatingRenderer) {
			((DelegatingRenderer) this.renderer).setRealRenderer(renderer);
		}
		else {
			this.renderer = renderer;
		}
	}
	
	/**
	 * 
	 * @param renderer
	 */
	public DelegatingRenderer(Renderer renderer) {
		this.renderer = renderer;
	}
	
	/**
	 * 
	 * @return
	 */
	protected final Renderer getDelegate() {
		return this.renderer;
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean isSameDelegator() {
		return getClass().isInstance(getDelegate());
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public void definePropertyDescriptor(PropertyDescriptor<?> propertyDescriptor) throws IllegalArgumentException {
		renderer.definePropertyDescriptor(propertyDescriptor);
	}

	@Override
	public PropertyDescriptor<?> getPropertyDescriptor(String name) {
		return renderer.getPropertyDescriptor(name);
	}
	
	@Override
	public List<PropertyDescriptor<?>> getPropertyDescriptors() {
		return renderer.getPropertyDescriptors();
	}

	@Override
	public <T> T getProperty(PropertyDescriptor<T> propertyDescriptor) {
		return renderer.getProperty(propertyDescriptor);
	}
	
	@Override
	public <T> void setProperty(PropertyDescriptor<T> propertyDescriptor, T value) {
		renderer.setProperty(propertyDescriptor, value);
	}

	@Override
	public Map<PropertyDescriptor<?>, Object> getPropertiesByPropertyDescriptor() {
		return renderer.getPropertiesByPropertyDescriptor();
	}

	@Override
	public boolean hasDescriptor(PropertyDescriptor<?> descriptor) {
		return renderer.hasDescriptor(descriptor);
	}

	@Override
	public boolean usesDefaultValues() {
		return renderer.usesDefaultValues();
	}

	@Override
	public void useDefaultValueFor(PropertyDescriptor<?> desc) {
		renderer.useDefaultValueFor(desc);
	}

	@Override
	public Set<PropertyDescriptor<?>> ignoredProperties() {
		return renderer.ignoredProperties();
	}

	@Override
	public String dysfunctionReason() {
		return renderer.dysfunctionReason();
	}

	@Override
	public String getName() {
		return renderer.getName();
	}

	@Override
	public void setName(String name) {
		renderer.setName(name);
	}

	@Override
	public String getDescription() {
		return renderer.getDescription();
	}

	@Override
	public String defaultFileExtension() {
		return renderer.defaultFileExtension();
	}

	@Override
	public void setDescription(String description) {
		renderer.setDescription(description);
	}

	@Override
	@SuppressWarnings("deprecation")
	public Map<String, String> getPropertyDefinitions() {
		return renderer.getPropertyDefinitions();
	}

	@Override
	public boolean isShowSuppressedViolations() {
		return renderer.isShowSuppressedViolations();
	}

	@Override
	public void setShowSuppressedViolations(boolean showSuppressedViolations) {
		renderer.setShowSuppressedViolations(showSuppressedViolations);
	}

	@Override
	public Writer getWriter() {
		return renderer.getWriter();
	}

	@Override
	public void setWriter(Writer writer) {
		renderer.setWriter(writer);
	}

	@Override
	public void start() throws IOException {
		renderer.start();
	}

	@Override
	public void startFileAnalysis(DataSource dataSource) {
		renderer.startFileAnalysis(dataSource);
	}

	@Override
	public void renderFileReport(Report report) throws IOException {
		renderer.renderFileReport(report);
	}

	@Override
	public void end() throws IOException {
		renderer.end();
	}

	@Override
	public void flush() throws IOException {
		renderer.flush();
	}
}

package com.tyn.jasca.analyzer.pmd;

import java.io.IOException;
import java.util.Properties;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.RulePriority;
import net.sourceforge.pmd.cli.PMDParameters;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.renderers.Renderer;

import com.tyn.jasca.analyzer.Configuration;
import com.tyn.jasca.analyzer.pmd.PmdConstant.RenderFormat;
import com.tyn.jasca.analyzer.pmd.renderer.DelegatingRenderer;
import com.tyn.jasca.analyzer.pmd.renderer.ProgressRenderer;

/**
 * 
 * @author S.J.H.
 */
public class PmdConfiguration implements Configuration {
	
	//--------------------------------------------------------------------------
	// optional values
	//--------------------------------------------------------------------------
	private boolean debug = false;
	private String encoding = "UTF-8";
	private String dir = null;
	private String uri = null;
	private String auxclasspath = null;
	private String language = null;
	private String version = null;
	private RulePriority minimumpriority = RulePriority.LOW;
	private DelegatingRenderer delegator = null;
	private boolean progress = false;
	private PmdProgress progressCallback = null;
	private RenderFormat format = RenderFormat.TEXT;
	private Class<? extends Renderer> renderer = null;
	private Properties properties = new Properties();
	private String reportfile = null;
	private String rulesets = null;
	private int threads = 1;
	//--------------------------------------------------------------------------
	// default values
	//--------------------------------------------------------------------------
	// -benchmark, -b >> true
	final boolean benchmark = false;
	// -stress, -S >> true
	final boolean stress = false;
	// -shortnames >> true
	final boolean shortnames = false;
	// -showsuppressed >> true
	final boolean showsuppressed = false;
	// -suppressmarker suppressmarker >> suppressmarker
	final String suppressmarker = PMD.SUPPRESS_MARKER;
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 * @return
	 */
	public boolean isDebug() {
		return debug;
	}
	
	/**
	 * <pre>
	 * -debug, -verbose, -D, -V
	 * (default = false)
	 * </pre>
	 * 
	 * @param debug Debug mode.
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEncoding() {
		return encoding;
	}
	
	/**
	 * <pre>
	 * -encoding, -e
	 * (default = UTF-8)
	 * </pre>
	 * 
	 * @param encoding Specifies the character set encoding of the source code files PMD is reading (i.e., UTF-8).
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 
	 * @return
	 */
	public String getDir() {
		return dir;
	}
	
	/**
	 * <pre>
	 * -dir, -d
	 * </pre>
	 * 
	 * @param dir Root directory for sources.
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUri() {
		return uri;
	}
	
	/**
	 * <pre>
	 * -uri, -u
	 * </pre>
	 * 
	 * @param uri Database URI for sources.
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAuxclasspath() {
		return auxclasspath;
	}
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @param auxclasspath Specifies the classpath for libraries used by the source code.
	 * This is used by the type resolution.
	 * Alternatively, a 'file://' URL to a text file containing path elements on consecutive lines can be specified.
	 */
	public void setAuxclasspath(String auxclasspath) {
		this.auxclasspath = auxclasspath;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * <pre>
	 * -language, -l
	 * </pre>
	 * 
	 * @param language Specify a language PMD should use.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * <pre>
	 * -version, -v
	 * </pre>
	 * 
	 * @param version Specify version of a language PMD should use.
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * 
	 * @return
	 */
	public RulePriority getMinimumpriority() {
		return minimumpriority;
	}
	
	/**
	 * <pre>
	 * -minimumpriority, -min
	 * (default = Low)
	 * </pre>
	 * 
	 * @param minimumpriority Rule priority threshold; rules with lower priority than configured here won't be used.
	 * Default is '5' which is the lowest priority.
	 */
	public void setMinimumpriority(RulePriority minimumpriority) {
		this.minimumpriority = minimumpriority;
	}
	
	/**
	 * 
	 * @return
	 */
	public DelegatingRenderer getDelegator() {
		return delegator;
	}
	
	/**
	 * 
	 * @param delegator
	 */
	public void setDelegator(DelegatingRenderer delegator) {
		this.delegator = delegator;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isProgress() {
		return progress;
	}
	
	/**
	 * <pre>
	 * additional option
	 * (default = false)
	 * </pre>
	 * 
	 * @param progress
	 */
	public void setProgress(boolean progress) {
		if (progressCallback != null && !progress) {
			throw new IllegalStateException();
		}
		this.progress = progress;
	}
	
	/**
	 * 
	 * @param progressCallback
	 */
	public void setProgress(PmdProgress progressCallback) {
		setProgressCallback(progressCallback);
	}
	
	/**
	 * 
	 * @return
	 */
	public PmdProgress getProgressCallback() {
		return progressCallback;
	}
	
	/**
	 * 
	 * @param progressCallback
	 */
	public void setProgressCallback(PmdProgress progressCallback) {
		this.progress = true;
		this.progressCallback = progressCallback;
	}
	
	/**
	 * 
	 * @return
	 */
	public RenderFormat getFormat() {
		return format;
	}
	
	/**
	 * <pre>
	 * -format, -f
	 * (default = text)
	 * </pre>
	 * 
	 * @param format Report format type.
	 */
	public void setFormat(RenderFormat format) {
		this.format = format;
	}
	
	/**
	 * 
	 * @return
	 */
	public Class<? extends Renderer> getRenderer() {
		return renderer;
	}
	
	/**
	 * 
	 * @param renderer
	 */
	public void setRenderer(Class<? extends Renderer> renderer) {
		this.format = RenderFormat.CUSTOM;
		this.renderer = renderer;
	}
	
	/**
	 * 
	 * @return
	 */
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * <pre>
	 * -property, -P
	 * (default = {})
	 * </pre>
	 * 
	 * @param properties Define a property for the report format.
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getReportfile() {
		return reportfile;
	}
	
	/**
	 * <pre>
	 * -reportfile, -r
	 * (default = System.out)
	 * </pre>
	 * 
	 * @param reportfile Sends report output to a file; default to System.out.
	 */
	public void setReportfile(String reportfile) {
		this.reportfile = reportfile;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRulesets() {
		return rulesets;
	}
	
	/**
	 * <pre>
	 * -rulesets, -R
	 * (*)
	 * </pre>
	 * 
	 * @param rulesets Comma separated list of ruleset names to use.
	 */
	public void setRulesets(String rulesets) {
		this.rulesets = rulesets;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getThreads() {
		return threads;
	}
	
	/**
	 * <pre>
	 * -threads, -t
	 * (default = 1)
	 * </pre>
	 * 
	 * @param threads Sets the number of threads used by PMD.
	 */
	public void setThreads(int threads) {
		this.threads = threads;
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 * @param configuration
	 * 
	 * @see net.sourceforge.pmd.cli.PMDParameters#transformParametersIntoConfiguration(PMDParameters params)
	 */
	protected void transform(PMDConfiguration configuration) {
		
		if (dir == null && uri == null) {
			throw new IllegalArgumentException();
		}
		
		configuration.setInputPaths(dir);
		configuration.setInputUri(uri);
		configuration.setReportFormat(format != RenderFormat.CUSTOM ?
				format.getReportFormat() : renderer.getCanonicalName());
		configuration.setBenchmark(benchmark);
		configuration.setDebug(debug);
		configuration.setMinimumPriority(minimumpriority);
		configuration.setReportFile(reportfile);
		configuration.setReportProperties(properties);
		configuration.setReportShortNames(shortnames);
		configuration.setRuleSets(rulesets);
		configuration.setShowSuppressedViolations(showsuppressed);
		configuration.setSourceEncoding(encoding);
		configuration.setStressTest(stress);
		configuration.setSuppressMarker(suppressmarker);
		configuration.setThreads(threads);
		
		LanguageVersion languageVersion =
				LanguageRegistry.findLanguageVersionByTerseName(language + " " + version);
		if (languageVersion != null) {
			configuration.getLanguageVersionDiscoverer()
				.setDefaultLanguageVersion(languageVersion);
		}
		
		try {
			configuration.prependClasspath(auxclasspath);
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException();
		}
		
		//----------------------------------------------------------------------
		
		if (progress && progressCallback != null) {
			delegator = new ProgressRenderer(delegator, progressCallback);
		}
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public void addOption(String... args) {
		throw new UnsupportedOperationException();
	}
}

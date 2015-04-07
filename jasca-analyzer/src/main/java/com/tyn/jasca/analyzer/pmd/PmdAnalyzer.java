package com.tyn.jasca.analyzer.pmd;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Handler;
import java.util.logging.Level;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.ReportListener;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSetFactory;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.RulesetsFactoryUtils;
import net.sourceforge.pmd.benchmark.Benchmark;
import net.sourceforge.pmd.benchmark.Benchmarker;
import net.sourceforge.pmd.lang.Language;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.LanguageVersionDiscoverer;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.stat.Metric;
import net.sourceforge.pmd.util.IOUtil;
import net.sourceforge.pmd.util.datasource.DataSource;
import net.sourceforge.pmd.util.log.ConsoleLogHandler;
import net.sourceforge.pmd.util.log.ScopedLogHandlersManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.analyzer.Configuration;
import com.tyn.jasca.analyzer.pmd.renderer.DelegatingRenderer;
import com.tyn.jasca.analyzer.pmd.renderer.ProgressRenderer;

/**
 * 
 * @author S.J.H.
 */
public class PmdAnalyzer extends Analyzer {
	
	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(PmdAnalyzer.class);
//	private static final Logger LOG = Logger.getLogger(PmdAnalyzer.class.getName());
	
	private PMDConfiguration internalConfiguration;
	
	private DelegatingRenderer delegator = null;
	private boolean progress = false;
	
	public PmdAnalyzer() {
		internalConfiguration = new PMDConfiguration();
	}
	
	@Override
	public void applyConfiguration(Configuration configuration) {
		PmdConfiguration pmdConfiguration = (PmdConfiguration) configuration;
		pmdConfiguration.transform(internalConfiguration);
		
		/**
		 * added configurations
		 */
		delegator = pmdConfiguration.getDelegator();
		progress = pmdConfiguration.isProgress();
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.analyzer.Analyzer#execute()
	 */
	@Override
	public void execute() {
		Handler logHandler = null;
		ScopedLogHandlersManager logHandlerManager = null;
		if (internalConfiguration.isDebug()) {
			logHandler = new ConsoleLogHandler();
			logHandlerManager = new ScopedLogHandlersManager(Level.FINER, logHandler);
		}
		
		log.debug("PMD analyzer start");
		
		/**
		 * execute PMD
		 */
		int violationsCount = doPMD();
		
		log.debug("PMD analyzer finished");
		
		if (internalConfiguration.isDebug()) {
			logHandlerManager.close();
			logHandlerManager = null;
			logHandler = null;
		}
		
		
		log.debug("violationsCount : {}", violationsCount);
	}
	
	/**
	 * 
	 * @return number of violations found.
	 * @see net.sourceforge.pmd.PMD#doPMD(PMDConfiguration configuration)
	 */
	private int doPMD() {
		// Load the RuleSets
		RuleSetFactory ruleSetFactory = RulesetsFactoryUtils.getRulesetFactory(internalConfiguration);
		RuleSets ruleSets = RulesetsFactoryUtils.getRuleSetsWithBenchmark(internalConfiguration.getRuleSets(), ruleSetFactory);
		if (ruleSets == null) {
			return 0;
		}
		
		Set<Language> languages = getApplicableLanguages(internalConfiguration, ruleSets);
		List<DataSource> files = PMD.getApplicableFiles(internalConfiguration, languages);
		
		long reportStart = System.nanoTime();
		try {
			Renderer renderer = internalConfiguration.createRenderer();
			/*******************************************************************
			 * S.J.H.
			 * for DelegatingRenderer & PmdProgress
			 */
			if (delegator != null) {
				delegator.setRealRenderer(renderer);
				renderer = delegator;
			}
			if (progress && renderer instanceof ProgressRenderer) {
				renderer.setProperty(ProgressRenderer.TOTAL_COUNT_PROPERTY, files.size());
			}
			/******************************************************************/
			List<Renderer> renderers = new LinkedList<Renderer>();
			renderers.add(renderer);
			
			renderer.setWriter(IOUtil.createWriter(internalConfiguration.getReportFile()));
			renderer.start();
			
			Benchmarker.mark(Benchmark.Reporting, System.nanoTime() - reportStart, 0);
			
			RuleContext ctx = new RuleContext();
			final AtomicInteger violations = new AtomicInteger(0);
			ctx.getReport().addListener(new ReportListener() {
				@Override
				public void ruleViolationAdded(RuleViolation ruleViolation) {
					violations.incrementAndGet();
				}
				@Override
				public void metricAdded(Metric metric) {
				}
			});
			
			PMD.processFiles(internalConfiguration, ruleSetFactory, files, ctx, renderers);
			
			reportStart = System.nanoTime();
			renderer.end();
			renderer.flush();
			
			return violations.get();
		}
		catch (Exception e) {
			log.error("PMD engine execute error", e);
			
			return 0;
		}
		finally {
			Benchmarker.mark(Benchmark.Reporting, System.nanoTime() - reportStart, 0);
		}
	}
	
	/**
	 * 
	 * @param configuration
	 * @param ruleSets
	 * @return
	 * 
	 * @see net.sourceforge.pmd.PMD#getApplicableLanguages(PMDConfiguration configuration, RuleSets ruleSets)
	 */
	private static Set<Language> getApplicableLanguages(PMDConfiguration configuration, RuleSets ruleSets) {
		Set<Language> languages = new HashSet<Language>();
		LanguageVersionDiscoverer discoverer = configuration.getLanguageVersionDiscoverer();
		
		for (Rule rule : ruleSets.getAllRules()) {
			Language language = rule.getLanguage();
			if (languages.contains(language)) {
				continue;
			}
			LanguageVersion version = discoverer.getDefaultLanguageVersion(language);
			if (RuleSet.applies(rule, version)) {
				languages.add(language);
				if (log.isDebugEnabled()) {
					log.debug("Using {} vertion: {}", language.getShortName(), version.getShortName());
				}
//				if (LOG.isLoggable(Level.FINE)) {
//					LOG.fine("Using " + language.getShortName() + " version: " + version.getShortName());
//				}
			}
		}
		return languages;
	}
}

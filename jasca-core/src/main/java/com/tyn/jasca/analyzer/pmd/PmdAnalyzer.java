package com.tyn.jasca.analyzer.pmd;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSetFactory;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.RulesetsFactoryUtils;
import net.sourceforge.pmd.benchmark.Benchmark;
import net.sourceforge.pmd.benchmark.Benchmarker;
import net.sourceforge.pmd.cli.PMDCommandLineInterface;
import net.sourceforge.pmd.lang.Language;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.LanguageVersionDiscoverer;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.util.IOUtil;
import net.sourceforge.pmd.util.datasource.DataSource;

import com.tyn.jasca.analyzer.Analyzer;
import com.tyn.jasca.analyzer.Configuration;

/**
 * 
 * @author S.J.H.
 */
public class PmdAnalyzer extends Analyzer {
	
	private static final Logger LOG = Logger.getLogger(PmdAnalyzer.class.getName());
	
	private PMDConfiguration internalConfiguration;
	private boolean progress;
	
	public PmdAnalyzer() {
		internalConfiguration = new PMDConfiguration();
	}
	
	@Override
	public void applyConfiguration(Configuration configuration) {
		PmdConfiguration pmdConfiguration = (PmdConfiguration) configuration;
		pmdConfiguration.transform(internalConfiguration);
		progress = pmdConfiguration.isProgress();
	}
	
	/**
	 * 
	 * @see com.tyn.jasca.analyzer.Analyzer#execute()
	 * @see net.sourceforge.pmd.PMD#doPMD(PMDConfiguration configuration)
	 */
	@Override
	public void execute() {
		/*
		PMD.doPMD(internalConfiguration);
		*/
		
		// Load the RuleSets
		RuleSetFactory ruleSetFactory = RulesetsFactoryUtils.getRulesetFactory(internalConfiguration);
		RuleSets ruleSets = RulesetsFactoryUtils.getRuleSetsWithBenchmark(internalConfiguration.getRuleSets(), ruleSetFactory);
		if (ruleSets == null) {
			return;
		}
		
		Set<Language> languages = getApplicableLanguages(internalConfiguration, ruleSets);
		List<DataSource> files = PMD.getApplicableFiles(internalConfiguration, languages);
		
		long reportStart = System.nanoTime();
		try {
			Renderer renderer = internalConfiguration.createRenderer();
			List<Renderer> renderers = new LinkedList<Renderer>();
			renderers.add(renderer);
			
			/**
			 * S.J.H.
			 * for progress..
			 */
			if (progress && renderer instanceof PmdProgress) {
				((PmdProgress) renderer).startAnalyze(files.size());
			}
			/***/
			
			renderer.setWriter(IOUtil.createWriter(internalConfiguration.getReportFile()));
			renderer.start();
			
			Benchmarker.mark(Benchmark.Reporting, System.nanoTime() - reportStart, 0);
			
			RuleContext ctx = new RuleContext();
			
			PMD.processFiles(internalConfiguration, ruleSetFactory, files, ctx, renderers);
			
			reportStart = System.nanoTime();
			renderer.end();
			renderer.flush();
		}
		catch (Exception e) {
			String message = e.getMessage();
			if (message != null) {
				LOG.severe(message);
			}
			else {
				LOG.log(Level.SEVERE, "Exception during processing", e);
			}
			LOG.log(Level.FINE, "Exception during processing", e);
			LOG.info(PMDCommandLineInterface.buildUsageText());
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
				if (LOG.isLoggable(Level.FINE)) {
					LOG.fine("Using " + language.getShortName() + " version: " + version.getShortName());
				}
			}
		}
		return languages;
	}
}

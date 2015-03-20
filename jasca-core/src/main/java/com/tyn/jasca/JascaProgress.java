package com.tyn.jasca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.analyzer.pmd.PmdProgress;

import edu.umd.cs.findbugs.FindBugsProgress;

/**
 * 
 * @author S.J.H.
 */
public class JascaProgress implements FindBugsProgress, PmdProgress {

	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(JascaProgress.class);
	
	
	private static void show(String s) {
		log.info(s);
	}
	
	private int percent(int n, int t, int s, int a) {
		return (int) (((double) n / t / a + ((double) (s - 1) / a)) * 100);
	}
	
	private int percent(int n, int t) {
		return percent(n, t, 1, 1);
	}
	
	//--------------------------------------------------------------------------
	
	int stepTotal = 0;
	int stepPass = 0;
	int classTotal = 0;
	int classPass = 0;
	
	@Override
	public void reportNumberOfArchives(int numArchives) {
	}
	
	@Override
	public void startArchive(String name) {
	}
	
	@Override
	public void finishArchive() {
	}
	
	@Override
	public void predictPassCount(int[] classesPerPass) {
		stepTotal = classesPerPass.length;
	}
	
	@Override
	public void startAnalysis(int numClasses) {
		classTotal = numClasses;
		++stepPass;
	}
	
	@Override
	public void finishClass() {
		show(String.format("FB  Analyze(Step:%d/%d) > [%4d / %4d] [%3d%%]",
				stepPass, stepTotal, ++classPass, classTotal, percent(classPass, classTotal)));
	}
	
	@Override
	public void finishPerClassAnalysis() {
		classPass = 0;
	}
	
	//--------------------------------------------------------------------------
	
	private int totalCount;
	
	@Override
	public void startAnalyze(int totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public void analyzeFile(int passCount) {
		show(String.format("PMD Analyze           > [%4d / %4d] [%3d%%]",
				passCount, totalCount, percent(passCount, totalCount)));
	}

	@Override
	public void finishAnalyze() {
		
	}
}

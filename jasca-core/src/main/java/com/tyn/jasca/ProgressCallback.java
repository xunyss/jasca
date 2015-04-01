package com.tyn.jasca;

import com.tyn.jasca.analyzer.pmd.PmdProgress;

import edu.umd.cs.findbugs.FindBugsProgress;

/**
 * FINDBUGS, PMD 엔진의 콜백 지원
 * 
 * @author S.J.H.
 */
public abstract class ProgressCallback implements
	FindBugsProgress,
	PmdProgress {
	
}

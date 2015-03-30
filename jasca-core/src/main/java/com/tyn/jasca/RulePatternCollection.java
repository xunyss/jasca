package com.tyn.jasca;

import java.util.HashMap;
import java.util.Map;

import com.tyn.jasca.analyzer.Analyzer.AnalyzerEngine;
import com.tyn.jasca.common.Utils;

/**
 * 
 * @author S.J.H.
 */
public class RulePatternCollection {
	
	/**
	 * singleton
	 */
	private static class Holder {
		private static final RulePatternCollection instance = new RulePatternCollection();
	}
	
	private static final String KEY_DELIM = ":";
	
	private static final String getKey(RulePattern rulePattern) {
		return getKey(rulePattern.getAnalyzerEngine(), rulePattern.getTypename());
	}
	
	private static final String getKey(AnalyzerEngine analyzerEngine, String typename) {
		return analyzerEngine.toString() + KEY_DELIM + typename;
	}
	
	
	/**
	 * 
	 */
	private Map<String, RulePattern> rulePatterns = null;
	
	private RulePatternCollection() {
		rulePatterns = new HashMap<String, RulePattern>();
	}
	
	public static RulePatternCollection getInstance() {
		return Holder.instance;
	}
	
	/**
	 * 
	 * @param rulePattern
	 * @return
	 */
	public RulePattern get(RulePattern rulePattern) {
		return get(rulePattern.getAnalyzerEngine(), rulePattern.getTypename());
	}
	
	/**
	 * 
	 * @param analyzerEngine
	 * @param typename
	 * @return
	 */
	public RulePattern get(AnalyzerEngine analyzerEngine, String typename) {
		String patternKey = getKey(analyzerEngine, typename);
		RulePattern rulePattern = rulePatterns.get(patternKey);
		
		if (rulePattern == null) {
			rulePattern = new RulePattern(analyzerEngine, typename);
			rulePatterns.put(patternKey, rulePattern);
		}
		
		return rulePattern;
	}
	
	/**
	 * 
	 * @param rulePattern
	 */
	public void register(RulePattern rulePattern) {
		if (rulePattern.getAnalyzerEngine() == null || Utils.isEmpty(rulePattern.getTypename())
				|| rulePattern.getSeverity() == null) {
			throw new JascaException("등록할 수 없는 RulePattern 입니다.");
		}
				
		rulePattern.setRegistered(true);
		String patternKey = getKey(rulePattern);
		rulePatterns.put(patternKey, rulePattern);
	}
}

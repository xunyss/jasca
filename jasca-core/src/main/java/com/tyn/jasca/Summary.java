package com.tyn.jasca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tyn.jasca.rules.Category;
import com.tyn.jasca.rules.Rule;

/**
 * 
 * @author S.J.H.
 */
public class Summary {
	
	private int violationCount = 0;
	private int[] severityCounts = null;
	
	private List<CategoryCounter> categoryCounterList = null;
//	private List<RuleCounter> ruleCounterList = null;
	
	public int getViolationCount() {
		return violationCount;
	}

	public void setViolationCount(int violationCount) {
		this.violationCount = violationCount;
	}

	public int[] getSeverityCounts() {
		return severityCounts;
	}

	public void setSeverityCounts(int[] severityCounts) {
		this.severityCounts = severityCounts;
	}

	public List<CategoryCounter> getCategoryCounterList() {
		return categoryCounterList;
	}

	public void setCategoryCounterList(List<CategoryCounter> categoryCounterList) {
		this.categoryCounterList = categoryCounterList;
	}
	
	private void sorts() {
		/**
		 * 심각도별 요약
		 */
		Collections.sort(categoryCounterList, new Comparator<CategoryCounter>() {
			@Override
			public int compare(CategoryCounter cc1, CategoryCounter cc2) {
				return cc1.getCategory().compareTo(cc2.getCategory());
			}
		});
	}
	
	/**
	 * 
	 * @param results
	 * @return
	 */
	public static Summary summary(Results results) {
		
		int total = 0;
		int[] severitySummary = new int[Severity.values().length];
		Map<Category, Integer> categorySummary = new HashMap<Category, Integer>();
		
		for (Violation violation : results) {
			Rule rule = violation.getRule();
			
			/*
			 * 전체
			 */
			total++;
			
			/*
			 * 심각도별
			 */
			severitySummary[rule.getSeverity().getValue() - 1]++;
			
			/*
			 * 카테고리별
			 */
			Category category = rule.getCategory();
			if (categorySummary.get(category) != null) {
				categorySummary.put(category, categorySummary.get(category).intValue() + 1);
			}
			else {
				categorySummary.put(category, 1);
			}
			
			/*
			 * TODO: 룰별
			 */
		}
		
		/*
		 * 카테고리별 요약
		 */
		List<CategoryCounter> categoryCounterList = new ArrayList<CategoryCounter>();
		Iterator<Category> itr = categorySummary.keySet().iterator();
		while (itr.hasNext()) {
			Category category = itr.next();
			categoryCounterList.add(new CategoryCounter(category, categorySummary.get(category)));
		}
		
		Summary summary = new Summary();
		summary.setViolationCount(total);
		summary.setSeverityCounts(severitySummary);
		summary.setCategoryCounterList(categoryCounterList);
		
		summary.sorts();
		return summary;
	}
	
	
	/**
	 * 
	 * @author S.J.H.
	 */
	public static class CategoryCounter {
		private Category category = null;
		private int count = 0;
		
		public CategoryCounter(Category category, int count) {
			this.category = category;
			this.count = count;
		}
		public Category getCategory() {
			return category;
		}
		public int getCount() {
			return count;
		}
	}
	
	/**
	 * 
	 * @author S.J.H.
	 */
	public static class RuleCounter {
		private Rule rule = null;
		private int count = 0;
		
		public RuleCounter(Rule rule, int count) {
			this.rule = rule;
			this.count = count;
		}
		public Rule getRule() {
			return rule;
		}
		public int getCount() {
			return count;
		}
	}
}

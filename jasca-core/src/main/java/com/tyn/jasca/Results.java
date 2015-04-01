package com.tyn.jasca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author S.J.H.
 * 
 * @ TODO make this thread safe
 * @ TODO JASCA context 적용
 * @ TODO summary 수집
 */
public class Results implements Iterable<Violation> {
	
	/**
	 * singleton
	 */
	private static class Holder {
		private static final Results instance = new Results();
	}
	
	/**
	 * 
	 * @return
	 */
	public static Results getInstance() {
		return Holder.instance;
	}
	
	/**
	 * 
	 */
	private Results() {
		
	}
	
	
	/**
	 * 
	 */
	private List<Violation> violations = null;
	
	/**
	 * 
	 */
	public void create() {
		if (violations != null) {
			clear();
		}
		violations = new ArrayList<Violation>();
	}
	
	/**
	 * 
	 * @param violation
	 * @return
	 */
	public boolean add(Violation violation) {
		return violations.add(violation);
	}
	
	/**
	 * 
	 */
	public void clear() {
		violations.clear();
	}
	
	/**
	 * 
	 */
	public void sort() {
		Collections.sort(violations, new Comparator<Violation>() {
			@Override
			public int compare(Violation violation1, Violation violation2) {
				return violation1.getRulePattern().compareTo(violation2.getRulePattern());
			}
		});
	}
	
	/**
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Violation> iterator() {
		return violations.iterator();
	}
}

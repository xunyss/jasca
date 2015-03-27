package com.tyn.jasca;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: make this thread safe
 * TODO: summary ¼öÁý
 * 
 * @author S.J.H.
 */
public class ViolationResult {
	
	private static class Holder {
		private static final ViolationResult instance = new ViolationResult();
	}
	
	public static ViolationResult getInstance() {
		return Holder.instance;
	}
	
	private ViolationResult() {
		
	}
	
	
	private List<Violation> violations = null;
	
	public List<Violation> getViolations() {
		return violations;
	}
	
	public void create() {
		if (violations != null) {
			clear();
		}
		violations = new ArrayList<Violation>();
	}
	
	public boolean add(Violation violation) {
		return violations.add(violation);
	}
	
	public void clear() {
		violations.clear();
	}
}

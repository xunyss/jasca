package com.tyn.jasca;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author S.J.H.
 */
public class MyListener {
	
	static List<Violation> list = new ArrayList<Violation>();
	
	public static void add(Violation violation) {
		list.add(violation);
	}
}

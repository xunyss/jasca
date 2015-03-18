package com.tyn.jasca.test;

import java.util.Iterator;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.SortedBugCollection;

public class BugCollectionTest {

	public static void main(String[] args) throws Exception {
		BugCollection bc = new SortedBugCollection();
		bc.readXML("D:/securecoding/workspace/jasca/target/findbugs.xml");
		
		int i = 0;
		Iterator<BugInstance> bi = bc.iterator();
		while (bi.hasNext()) {
			BugInstance b = bi.next();
			System.out.println((++i) + " : " + b.getMessage());
		}
	}
}

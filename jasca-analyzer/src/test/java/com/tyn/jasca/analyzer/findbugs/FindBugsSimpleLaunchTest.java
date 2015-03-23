package com.tyn.jasca.analyzer.findbugs;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;

import edu.umd.cs.findbugs.BugCollection;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.FindBugs;
import edu.umd.cs.findbugs.FindBugs2;
import edu.umd.cs.findbugs.Plugin;
import edu.umd.cs.findbugs.SortedBugCollection;
import edu.umd.cs.findbugs.TextUICommandLine;

/**
 * 
 * @author S.J.H.
 */
public class FindBugsSimpleLaunchTest {
	
	@Test
	public void runFindBugs() throws Exception {
		String[] options = {
			//	"-textui",
			//	"-jvmArgs", "-Dfindbugs.debug=true",
				
				"-xml" + ":withMessages",
			//	"-html",
				"-low",
				"-nested" + ":false",
			//	"-debug",
				"-progress",
				"-output", "D:\\xdev\\git\\jasca\\jasca-analyzer\\target\\simple-findbugs.xml",
				"D:\\securecoding\\workspace\\sampleweb"
		};
		
		Plugin.loadCustomPlugin(new File("D:/securecoding/maven/repository/com/h3xstream/findsecbugs/findsecbugs-plugin/1.3.1/findsecbugs-plugin-1.3.1.jar"), null);
		
		FindBugs2 findBugs = new FindBugs2();
		TextUICommandLine commandLine = new TextUICommandLine();
		FindBugs.processCommandLine(commandLine, options, findBugs);
		FindBugs.runMain(findBugs, commandLine);
	}
	
	@Test
	public void readBugXML() throws Exception {
		BugCollection bc = new SortedBugCollection();
		bc.readXML("D:\\xdev\\git\\jasca\\jasca-analyzer\\target\\simple-findbugs.xml");
		
		int i = 0;
		Iterator<BugInstance> bi = bc.iterator();
		while (bi.hasNext()) {
			BugInstance b = bi.next();
			System.out.println((++i) + " : " + b.getMessage());
		}
	}
}

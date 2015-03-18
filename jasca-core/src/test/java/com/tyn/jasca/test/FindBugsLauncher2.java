package com.tyn.jasca.test;

import java.io.File;

import org.junit.Test;

import edu.umd.cs.findbugs.FindBugs;
import edu.umd.cs.findbugs.FindBugs2;
import edu.umd.cs.findbugs.Plugin;
import edu.umd.cs.findbugs.TextUICommandLine;

public class FindBugsLauncher2 {
	
	@Test
	public void runFindBugs() throws Exception {
		String[] options = {
			//	"-textui",
				"-xml" + ":withMessages",
			//	"-html",
				"-low",
				"-nested" + ":false",
			//	"-debug",
			//	"-jvmArgs", "-Dfindbugs.debug=true",
				"-progress",
				"-output", "D:\\securecoding\\workspace\\jasca\\target\\findbugs.xml",
				"D:\\securecoding\\workspace\\sampleweb"
		};
		
		Plugin.loadCustomPlugin(new File("D:/securecoding/maven/repository/com/h3xstream/findsecbugs/findsecbugs-plugin/1.3.1/findsecbugs-plugin-1.3.1.jar"), null);
		
		FindBugs2 findBugs = new FindBugs2();
		TextUICommandLine commandLine = new TextUICommandLine();
		FindBugs.processCommandLine(commandLine, options, findBugs);
		FindBugs.runMain(findBugs, commandLine);
	}
}

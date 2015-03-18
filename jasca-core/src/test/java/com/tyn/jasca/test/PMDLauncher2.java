package com.tyn.jasca.test;

import net.sourceforge.pmd.cli.PMDCommandLineInterface;

import org.junit.Test;

public class PMDLauncher2 {

	@Test
	public void runPMD() throws Exception {
		String[] options = {
				"-d", "D:\\securecoding\\workspace\\sampleweb",
			//	"-debug",
				"-f", "html",
				"-r", "D:\\securecoding\\workspace\\jasca\\target\\pmd2.html",
			//	"-rulesets", "java-basic,java-design,jsp-basic"
			//	"-rulesets", "jasca_rules.xml"
				"-rulesets", "java-basic,java-design,jsp-basic" +
						",java-codesize,java-empty,java-j2ee,java-javabeans,java-migrating" +
						",java-strings,java-sunsecure,java-typeresolution,java-braces,java-clone,java-comments,java-strictexception"
		};
		
		PMDCommandLineInterface.run(options);
	}
}

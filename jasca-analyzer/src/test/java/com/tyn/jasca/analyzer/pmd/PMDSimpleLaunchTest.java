package com.tyn.jasca.analyzer.pmd;

import net.sourceforge.pmd.cli.PMDCommandLineInterface;

import org.junit.Test;

public class PMDSimpleLaunchTest {

//	@Test
	public void runPMD() throws Exception {
		String[] options = {
				"-d", "D:\\securecoding\\workspace\\sampleweb",
				"-debug",
				"-f", "xml",
				"-r", "D:\\xdev\\git\\jasca\\jasca-analyzer\\target\\simple-pmd.xml",
			//	"-rulesets", "java-basic,java-design,jsp-basic"
			//	"-rulesets", "jasca_rules.xml"
				"-rulesets", "java-basic,java-design,jsp-basic" +
						",java-codesize,java-empty,java-j2ee,java-javabeans,java-migrating" +
						",java-strings,java-sunsecure,java-typeresolution,java-braces,java-clone,java-comments,java-strictexception"
		};
		
		PMDCommandLineInterface.run(options);
	}
}

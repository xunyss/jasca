package com.tyn.jasca.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;

public class PMDLauncher {
	
	private String[] getCommand() {
		String pmdHome = "D:\\securecoding\\pmd-bin-5.2.3";
		String pmdExec = "\\bin\\pmd.bat";
		
		String[] options = {
				"cmd.exe", "/c",
				pmdHome + pmdExec,
				"-d", "D:\\securecoding\\workspace\\sampleweb",
				"-debug",
				"-f", "html",
				"-r", "D:\\securecoding\\workspace\\jasca\\target\\pmd.html",
				"-rulesets", "java-basic,java-design,jsp-basic" +
						",java-codesize,java-empty,java-j2ee,java-javabeans,java-migrating" +
						",java-strings,java-sunsecure,java-typeresolution,java-braces,java-clone,java-comments,java-strictexception"
		};
		
		return options;
	}
	
	@Test
	public void runPMD() throws Exception {
		Process process = Runtime.getRuntime().exec(getCommand());
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		br.close();
		process.waitFor();
	}
}

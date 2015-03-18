package com.tyn.jasca.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;

public class FindBugsLauncher {
	
	private String[] getCommand() {
		String findbugsHome = "D:\\securecoding\\findbugs-3.0.0";
		String findbugsExec = "\\bin\\findbugs.bat";
		
		String[] options = {
				"cmd.exe", "/c",
				findbugsHome + findbugsExec,
				"-textui",
			//	"-xml" + ":withMessages",
				"-html",
				"-low",
				"-nested" + ":false",
				"-debug",
				"-progress",
				"-output", "D:\\securecoding\\workspace\\jasca\\target\\findbugs.html",
				"D:\\securecoding\\workspace\\sampleweb"
		};
		
		return options;
	}
	
	@Test
	public void runFindBugs() throws Exception {
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

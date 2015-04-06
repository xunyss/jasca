package com.tyn.jasca;

import org.junit.Test;

/**
 * 
 * @author S.J.H.
 */
public class JascaTest {
	
	@Test
	public void jascaMainTest() {
		
		String[] args = {
			"-progress",
			"-level", "low",
			"-format", "html",
			"-output",
			"D:/xdev/git/jasca/jasca-core/target/jasca.html",
		//	"D:/xdev/git/jasca/jasca-core/target/jasca.xlsx",
			
			"D:/securecoding/workspace/sampleweb"
		//	"D:/securecoding/workspace/sampleweb/build/classes/com/tyn/wweb/W41_2.class"
		//	"D:/securecoding/workspace/sampleweb/src/com/tyn/wweb/test"
		};
		
		Jasca.main(args);
	}
}

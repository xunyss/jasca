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
			"D:/securecoding/workspace/sampleweb",
		//	"D:/xdev/git/jasca/jasca-core/target/jasca.html"
			"D:/xdev/git/jasca/jasca-core/target/jasca.xlsx"
		};
		
		Jasca.main(args);
	}
}

package com.tyn.jasca;

import org.junit.Test;

public class JascaTest {
	
	@Test
	public void jascaMainTest() {
		
		String[] args = {
			"D:/securecoding/workspace/sampleweb",
			"D:/xdev/git/jasca/jasca-core/target/jasca.html"
		};
		
		Jasca.main(args);
	}
}

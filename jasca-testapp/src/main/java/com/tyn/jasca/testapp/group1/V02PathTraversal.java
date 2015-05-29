package com.tyn.jasca.testapp.group1;

import java.io.File;

public class V02PathTraversal {
	
	public boolean deleteFile(String filename) {
		File file = new File("D:/Downloads/" + filename);
		return file.delete();
	}
}

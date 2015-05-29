package com.tyn.jasca.testapp.group5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.tyn.jasca.testapp.common.Log;
import com.tyn.jasca.testapp.common.Printer;

public class V37NULLPointerDereference {

	public void printFile(String filename) throws IOException {
		FileReader filereader = null;
		try {
			File file = new File(filename);
			filereader = new FileReader(file);
			Printer.print(filereader.read());
		}
		catch (FileNotFoundException fnfe) {
			Log.log("오류: 파일없음");
		}
		finally {
			filereader.close();
		}
	}
}

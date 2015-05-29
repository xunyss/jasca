package com.tyn.jasca.testapp.group1;

import java.io.IOException;

import com.tyn.jasca.testapp.common.Log;

public class V04CommandInjection {

	public void execute(String cmd) {
		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("cmd.exe", new String[]{"/c", cmd});
		}
		catch (IOException ioe) {
			Log.log("¿¡·¯");
		}
	}
}

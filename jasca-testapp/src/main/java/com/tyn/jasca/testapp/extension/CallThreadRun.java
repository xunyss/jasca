package com.tyn.jasca.testapp.extension;

import com.tyn.jasca.testapp.common.Printer;

public class CallThreadRun {
	
	public static class MyJob implements Runnable {
		@Override
		public void run() {
			Printer.print("hello world");
		}
	}
	
	public Thread thread = new Thread(new MyJob());
	
	public void run() {
		thread.run();
	}
}

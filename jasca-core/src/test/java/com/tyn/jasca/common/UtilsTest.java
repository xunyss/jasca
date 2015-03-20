package com.tyn.jasca.common;

import java.io.FileWriter;
import java.io.Writer;

import net.sourceforge.pmd.util.IOUtil;

public class UtilsTest {
	
	public static void main(String[] args) {
		Writer w = IOUtil.createWriter();
		
		System.out.println(w);
		
		System.out.println(w.getClass().getName());
		
		System.out.println(w.getClass().equals(FileWriter.class));
	}
}

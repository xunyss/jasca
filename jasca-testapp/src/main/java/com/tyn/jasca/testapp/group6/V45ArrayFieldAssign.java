package com.tyn.jasca.testapp.group6;

import com.tyn.jasca.testapp.common.Printer;

public class V45ArrayFieldAssign {
	private String[] colors;
	private int size;
	
	public void setColors(String[] colors) {
		this.colors = colors;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void print() {
		Printer.print(colors.length + " " + size);
	}
}

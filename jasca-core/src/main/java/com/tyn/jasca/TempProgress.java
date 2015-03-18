package com.tyn.jasca;

import edu.umd.cs.findbugs.FindBugsProgress;

public class TempProgress implements FindBugsProgress {

	private static void w(String s) {
		System.out.println(s);
	}
	
	// 1
	// project 에 add 된 DIR 갯수
	@Override
	public void reportNumberOfArchives(int numArchives) {
		w("reportNumberOfArchives, " + numArchives);
	}
	
	// 2
	// project 에 add 된게 archive 임
	// WorkListItem(filesystem:D:\securecoding\workspace\sampleweb, true, SPECIFIED)
	@Override
	public void startArchive(String name) {
		w("startArchive, " + name);
	}
	
	// 3
	@Override
	public void finishArchive() {
		w("finishArchive");
	}
	
	// 4
	// 
	@Override
	public void predictPassCount(int[] classesPerPass) {
		w("predictPassCount, " + classesPerPass[0] + ", " + classesPerPass[1]);
	}
	
	// 5 분석시작
	// 5 6 7 반복 4의 인자로 넘어온 배열 사이즈만큼
	@Override
	public void startAnalysis(int numClasses) {
		w("startAnalysis, " + numClasses);
	}
	
	// 7 분석
	@Override
	public void finishClass() {
		w("finishClass");
	}
	
	//  6 분석
	@Override
	public void finishPerClassAnalysis() {
		w("finishPerClassAnalysis");
	}
}

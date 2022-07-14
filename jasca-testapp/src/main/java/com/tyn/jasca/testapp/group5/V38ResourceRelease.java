package com.tyn.jasca.testapp.group5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.tyn.jasca.testapp.common.Log;

public class V38ResourceRelease {
	
	public void queryData(String url) {
		try {
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			stmt.execute("select * from table");
		}
		catch (SQLException e) {
			Log.log("에러");
		}
	}
}

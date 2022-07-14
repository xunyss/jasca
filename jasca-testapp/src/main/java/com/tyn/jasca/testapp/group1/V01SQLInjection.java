package com.tyn.jasca.testapp.group1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.tyn.jasca.testapp.common.Log;

public class V01SQLInjection {
	
	public void selectDatabase(Connection conn, String userid) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute("select username from user where id = '" + userid + "'");
		}
		catch (SQLException sqle) {
			Log.log("에러");
		}
		finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			}
			catch (SQLException e) {
				Log.log("에러");
			}
		}
	}
}

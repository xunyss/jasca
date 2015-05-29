package com.tyn.jasca.testapp.group4;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.tyn.jasca.testapp.common.Log;

public class V34LogExceptionInfo {

	public void queryInfo(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute("select * from user_info");
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException e) {
					Log.log("¿¡·¯");
				}
			}
		}
	}
}

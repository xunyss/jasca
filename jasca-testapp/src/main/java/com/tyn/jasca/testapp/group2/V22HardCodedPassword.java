package com.tyn.jasca.testapp.group2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;

public class V22HardCodedPassword {
	
	public Connection DBConn() throws SQLException {
		Connection conn = 
				DriverManager.getConnection("dburl", "scott", "tiger");
		return conn;
	}
	
	public Properties bind() {
		Properties props = new Properties();
		props.put(Context.SECURITY_CREDENTIALS, "password");
		return props;
	}
}

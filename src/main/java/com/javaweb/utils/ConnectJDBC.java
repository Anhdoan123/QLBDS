package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectJDBC {
	static final String url = "jdbc:mysql://localhost:3306/estatebasic";
	static final String user = "root";
	static final String pass = "root";
	public static ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			Connection conn = DriverManager.getConnection(url,user,pass);
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			
		}
		return rs;
	}
	
	public static boolean executeUpdate(String sql) {
		try {
			Connection conn = DriverManager.getConnection(url,user,pass);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}

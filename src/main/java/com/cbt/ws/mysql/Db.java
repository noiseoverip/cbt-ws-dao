package com.cbt.ws.mysql;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class Db {

	public static Connection getConnection() {
		Connection conn = null;

		String userName = "root";
		String password = "";
		String url = "jdbc:mysql://localhost:3306/cbt";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = (Connection) DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			// For the sake of this tutorial, let's keep exception handling simple
			e.printStackTrace();
		} 
		return conn;
	}
}
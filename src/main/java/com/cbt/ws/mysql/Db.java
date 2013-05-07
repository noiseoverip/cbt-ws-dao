package com.cbt.ws.mysql;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

// TODO: add connection pool
public class Db {
	private static Connection connection;

	public static synchronized Connection getConnection() {
		if (null == connection) {

			String userName = "root";
			String password = "";
			String url = "jdbc:mysql://localhost:3306/cbt";

			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = (Connection) DriverManager.getConnection(url, userName, password);
			} catch (Exception e) {
				// For the sake of this tutorial, let's keep exception handling simple
				e.printStackTrace();
			}
		}
		return connection;
	}
}
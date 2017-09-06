package com.kafka.websocket.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLJDBCClient {

	private static Connection conn;

	public Connection createConnection(String hostAndDatabaseName, String userName, String password) {

		if (conn == null) {
			final String dbUrl = "jdbc:postgresql://" + hostAndDatabaseName;
			try {
				Class.forName("org.postgresql.Driver");
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(dbUrl, userName, password);
			} catch (SQLException sqlException) {
				System.out.println("Wyst�pi� wyj�tek w trakcie zestawiania po��czenia z baz�");
				System.out.println(sqlException.getCause());
			} catch (ClassNotFoundException classNotFoundException) {
				System.out.println(classNotFoundException.getCause());
			}
			return conn;
		} else {
			return conn;
		}
	}

	public void closeConnection(Connection conn) {
		System.out.println("Rozpoczynam dzia�anie funkcji closeConnection()");
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Po��czenie z baz� zamkni�to poprawnie");
			} catch (SQLException sqlException) {
				System.out.println("Wystapi� b��d podczas zamykania po��czenia z baz�");
				System.out.println(sqlException.getCause());
			}
		} else {
			System.out.println("Po��czenie nie jest nawi�zane, nie mo�e zosta� zamkni�te");
		}
	}

	public ResultSet getResultSetFromGivenQuery(Connection connection, String selectQuery) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(selectQuery);
		return rs;
	}
	
	

}

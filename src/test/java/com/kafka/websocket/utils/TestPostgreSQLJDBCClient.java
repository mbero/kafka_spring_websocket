package com.kafka.websocket.utils;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;

public class TestPostgreSQLJDBCClient {
	
	private PostgreSQLJDBCClient postgreSQLJDBCClient;
	
	@Before
	public void preparePostgreSQLJDBCClient(){
		postgreSQLJDBCClient = new PostgreSQLJDBCClient();
	}
	
	@Test
	public void testCreateConnection(){
		Connection connection = postgreSQLJDBCClient.createConnection("localhost:5433/bms_metadata_test", "postgres", "postgres");
		assertNotNull(connection);
	}
	
	@Test
	public void testGetResultSetFromGivenQuery(){
		Connection connection = postgreSQLJDBCClient.createConnection("localhost:5433/bms_metadata_test", "postgres", "postgres");
		ResultSet resultset = null;
		try {
			resultset = postgreSQLJDBCClient.getResultSetFromGivenQuery(connection, 
					"select * from \"Device\"  JOIN \"Location\" "
					+ "on \"Device\".location_id = \"Location\".id  "
					+ "JOIN \"Parameter\" on \"Parameter\".device_id = \"Device\".id "
					+ "JOIN \"Unit\" on \"Parameter\".unit_id = \"Unit\".id WHERE \"Device\".id = 1");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertNotNull(resultset);
	}
	
	
	
}

package com.kafka.websocket.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kafka.websockt.utils.model.ParameterMetadataSummary;

public class MetadataDBProcessingService {

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ParameterMetadataSummary getMetadaSummaryFromGivenResultSet(ResultSet rs) throws SQLException{
		ParameterMetadataSummary parameterMetadata = new ParameterMetadataSummary();
		while(rs.next()){
			String deviceName = rs.getString("name");
			parameterMetadata.setDeviceName(deviceName);
			String deviceDescription = rs.getString("description");
			parameterMetadata.setDeviceDescription(deviceDescription);
			String locationName = rs.getString("name");
			parameterMetadata.setLocationName(locationName);
			String locationDescription = rs.getString("description");
			parameterMetadata.setLocationDescription(locationDescription);
		}
		
		return parameterMetadata;
	}
	
	/**
	 * Returns result set with metadata based on readTagId
	 * @param readTagID
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getResultsSetWithMetadata(String readTagID) throws SQLException{
		PostgreSQLJDBCClient postgreSQLJDBCClient = new PostgreSQLJDBCClient();
		Connection connection = postgreSQLJDBCClient.createConnection("localhost:5433/bms_metadata_test", "postgres", "postgres");
		ResultSet resultSet = postgreSQLJDBCClient.getResultSetFromGivenQuery(connection, 	
				"select * from \"Device\"  JOIN \"Location\" " + "on \"Device\".location_id = \"Location\".id  "
				+ "JOIN \"Parameter\" on \"Parameter\".device_id = \"Device\".id "
				+ "JOIN \"Unit\" on \"Parameter\".unit_id = \"Unit\".id WHERE \"Device\".id = " + readTagID);
		
		return resultSet;
	}
	
	/**
	 * Executes proper SLQ query and makes proper transformation to get ParameterMetadataSummary object
	 * @param readTagID
	 * @return
	 * @throws SQLException 
	 */
	public ParameterMetadataSummary getParameterMetadataSummaryObjectByReadTagID(String readTagID) throws SQLException{
		ResultSet metadataSummaryResultSet = getResultsSetWithMetadata(readTagID);
		ParameterMetadataSummary parameterMetadataSummary = getMetadaSummaryFromGivenResultSet(metadataSummaryResultSet);
		
		return parameterMetadataSummary;
	}
}
	
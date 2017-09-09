package com.kafka.websocket.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kafka.websockt.utils.model.ParameterMetadataSummary;

public class MetadataDBProcessingService {

	public ParameterMetadataSummary getMetadaSummaryFromGivenResultSet(ResultSet rs) throws SQLException{
		ParameterMetadataSummary parameterMetadata = new ParameterMetadataSummary();
		while(rs.next()){
			String deviceName = rs.getString("name");
			parameterMetadata.setDeviceName(deviceName);
			String deviceDescription = rs.getString("description");
			parameterMetadata.setDeviceDescription(deviceDescription);
	
		}
		
		return parameterMetadata;
	}
	
	
	public ResultSet getResultsSetWithMetadata(String readTagID) throws SQLException{
		PostgreSQLJDBCClient postgreSQLJDBCClient = new PostgreSQLJDBCClient();
		Connection connection = postgreSQLJDBCClient.createConnection("localhost:5433/bms_metadata_test", "postgres", "postgres");
		ResultSet resultSet = postgreSQLJDBCClient.getResultSetFromGivenQuery(connection, "select * from public.'Device'  JOIN public.'Location' "
				+ "on public.'Device'.location_id = public.'Location'.id  "
				+ "JOIN public.'Parameter' on public.'Parameter'.device_id = public.'Device'.id"
				+ "JOIN public.'Unit' on public.'Parameter'.unit_id = public.'Unit'.id WHERE public.'Device'.id = 1");
		
		return resultSet;
	
		/*
		 * select * from public."Device"  JOIN public."Location" on public."Device".location_id = public."Location".id  
			       JOIN public."Parameter" on public."Parameter".device_id = public."Device".id
			       JOIN public."Unit" on public."Parameter".unit_id = public."Unit".id
WHERE public."Device".id = 1
		 */
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
	
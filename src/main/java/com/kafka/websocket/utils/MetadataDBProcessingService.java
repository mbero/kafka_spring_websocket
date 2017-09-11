package com.kafka.websocket.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kafka.websockt.utils.model.ParameterMetadataSummary;

public class MetadataDBProcessingService {
	
	private static PostgreSQLJDBCClient postgreSQLJDBCClient = new PostgreSQLJDBCClient();
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ParameterMetadataSummary getMetadaSummaryFromGivenResultSet(ResultSet rs) throws SQLException {
		ParameterMetadataSummary parameterMetadata = new ParameterMetadataSummary("","","","","","");

		while (rs.next()) {
			//rs.next();
			parameterMetadata = new ParameterMetadataSummary();
			Object[] row = new Object[16];
			for (int i = 1; i <= 16; ++i) {
				try {
					row[i - 1] = rs.getObject(i);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//rs.first();

					e.printStackTrace();
					break;
				}
			}
			parameterMetadata.setDeviceName(String.valueOf(row[1]));
			parameterMetadata.setDeviceDescription(String.valueOf(row[2]));
			parameterMetadata.setLocationName(String.valueOf(row[5]));
			parameterMetadata.setLocationDescription(String.valueOf(row[6]));
			parameterMetadata.setParameterName(String.valueOf(row[11]));
			parameterMetadata.setUnitName(String.valueOf(row[14]));
			
			
			//postgreSQLJDBCClient.closeConnection();
			//postgreSQLJDBCClient.closeStatement();
			//postgreSQLJDBCClient.closeResultset();
		}
		// [1, LCP, Klimatyzator miêdzyrzêdowy Rittal, 1, 1, Serwerownia,
		// Glowny budynek czesci serwerowej, null, 1, 1, 1, Server_IN1, 1,
		// 1, Celsius, °C]
		// rs.first();
		return parameterMetadata;

	}

	/**
	 * Returns result set with metadata based on readTagId
	 * 
	 * @param readTagID
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getResultsSetWithMetadata(String readTagID) throws SQLException {
		
		// TODO - parametrize MetadataDBProcessingService
		Connection connection = postgreSQLJDBCClient.createConnection("localhost:5433/bms_metadata_test", "postgres",
				"postgres");

		ResultSet resultSet = postgreSQLJDBCClient.getResultSetFromGivenQuery(connection,
				"select * from \"Device\"  JOIN \"Location\" " + "on \"Device\".location_id = \"Location\".id  "
						+ "JOIN \"Parameter\" on \"Parameter\".device_id = \"Device\".id "
						+ "JOIN \"Unit\" on \"Parameter\".unit_id = \"Unit\".id WHERE \"Parameter\".id = " + readTagID);
	
		return resultSet;
	}

	/**
	 * Executes proper SLQ query and makes proper transformation to get
	 * ParameterMetadataSummary object
	 * 
	 * @param readTagID
	 * @return
	 * @throws SQLException
	 */
	public ParameterMetadataSummary getParameterMetadataSummaryObjectByReadTagID(String readTagID) throws SQLException {
		ResultSet metadataSummaryResultSet = getResultsSetWithMetadata(readTagID);
		ParameterMetadataSummary parameterMetadataSummary = getMetadaSummaryFromGivenResultSet(metadataSummaryResultSet);
		
		return parameterMetadataSummary;
	}
}

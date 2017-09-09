package com.kafka.websocket.utils;

import java.sql.Array;
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
	public ParameterMetadataSummary getMetadaSummaryFromGivenResultSet(ResultSet rs) throws SQLException {
		ParameterMetadataSummary parameterMetadata = new ParameterMetadataSummary();
		rs.next();
		Object[] row = new Object[16];
		for (int i = 1; i <= 16; ++i) {
			row[i - 1] = rs.getObject(i);
		}
		parameterMetadata.setDeviceName(String.valueOf(row[1]));
		parameterMetadata.setDeviceDescription(String.valueOf(row[2]));
		parameterMetadata.setLocationName(String.valueOf(row[5]));
		parameterMetadata.setLocationDescription(String.valueOf(row[6]));
		parameterMetadata.setParameterName(String.valueOf(row[11]));
		parameterMetadata.setUnitName(String.valueOf(row[14]));

		// [1, LCP, Klimatyzator miêdzyrzêdowy Rittal, 1, 1, Serwerownia, Glowny budynek czesci serwerowej, null, 1, 1, 1, Server_IN1, 1, 1, Celsius, °C]

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
		PostgreSQLJDBCClient postgreSQLJDBCClient = new PostgreSQLJDBCClient();
		Connection connection = postgreSQLJDBCClient.createConnection("localhost:5433/bms_metadata_test", "postgres",
				"postgres");
		ResultSet resultSet = postgreSQLJDBCClient.getResultSetFromGivenQuery(connection,
				"select * from \"Device\"  JOIN \"Location\" " + "on \"Device\".location_id = \"Location\".id  "
						+ "JOIN \"Parameter\" on \"Parameter\".device_id = \"Device\".id "
						+ "JOIN \"Unit\" on \"Parameter\".unit_id = \"Unit\".id WHERE \"Device\".id = " + readTagID);

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
		ParameterMetadataSummary parameterMetadataSummary = getMetadaSummaryFromGivenResultSet(
				metadataSummaryResultSet);

		return parameterMetadataSummary;
	}
}

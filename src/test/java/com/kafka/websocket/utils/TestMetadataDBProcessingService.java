package com.kafka.websocket.utils;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Test;

import com.kafka.websockt.utils.model.ParameterMetadataSummary;

public class TestMetadataDBProcessingService {

	private MetadataDBProcessingService metadataProcessingservice;

	@Test
	public void prepareTestMetadataDBProcessingService() {
		metadataProcessingservice = new MetadataDBProcessingService();
		ParameterMetadataSummary parameterMetadataSumary = null;
		for (int i = 0; i < 15; i++) {
			try {
				parameterMetadataSumary = metadataProcessingservice.getParameterMetadataSummaryObjectByReadTagID("1");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			assertNotNull(parameterMetadataSumary);

		}
	}
}

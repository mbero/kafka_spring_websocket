package com.kafka.websocket.utils;
import org.junit.Test;

import com.kafka.websockt.utils.model.ParameterMetadataSummary;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;

public class TestMetadataDBProcessingService {
	
	private MetadataDBProcessingService metadataProcessingservice;

	@Test
	public void prepareTestMetadataDBProcessingService(){
		metadataProcessingservice = new MetadataDBProcessingService();
		ParameterMetadataSummary parameterMetadataSumary = null;
		try {
			parameterMetadataSumary = metadataProcessingservice.getParameterMetadataSummaryObjectByReadTagID("1");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(parameterMetadataSumary);
	}
}

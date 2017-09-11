package com.kafka.websocket.utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.websockt.utils.model.HistogramSummary;
import com.kafka.websockt.utils.model.ParameterMetadataSummary;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

@Component
public class DataGenerator implements ApplicationListener<BrokerAvailabilityEvent> {

	private final MessageSendingOperations<String> messagingTemplate;
	private final KafkaConsumer kafkaConsumer = new KafkaConsumer();
	private List<KafkaStream<byte[], byte[]>> streams = kafkaConsumer
			.getStreamsFromKafkaTopic("stream_processing_results");
	private MetadataDBProcessingService metadataProcessingService = new MetadataDBProcessingService();

	@Autowired
	public DataGenerator(final MessageSendingOperations<String> messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public void onApplicationEvent(final BrokerAvailabilityEvent event) {
	}

	/*
	 * @Scheduled(fixedDelay = 1000) public void sendDataUpdates() {
	 * 
	 * this.messagingTemplate.convertAndSend("/data", newRandom().nextInt(100));
	 * 
	 * }
	 */
	@Scheduled(fixedDelay = 100)
	public void sendDataUpdates() {
		ConsumerIterator<byte[], byte[]> it = streams.get(0).iterator();
		String messageJSON = new String(it.next().message());
		System.out.println(messageJSON);
		JSONObject currentKafkaRecordJSONObject = JSONUtils.getJSONObjectFromGivenString(messageJSON);
		Object currentReadTagID = currentKafkaRecordJSONObject.get("readTag_id");
		Integer readTagID = Integer.valueOf(String.valueOf(currentReadTagID));
		ParameterMetadataSummary parameterMetadataSummary = null;
		try {
			parameterMetadataSummary = metadataProcessingService.getParameterMetadataSummaryObjectByReadTagID(String.valueOf(readTagID));
		} catch (SQLException e) {
			System.out.print("Error occurred during processing metadata information for :"+ readTagID);
			e.printStackTrace();
		}
		Object currentMean = currentKafkaRecordJSONObject.get("mean");
		Double d = (Double) currentMean;
		Integer i = d.intValue(); // i becomes 5

		HistogramSummary histogramSummary = new HistogramSummary(readTagID, parameterMetadataSummary, i);
		String histogramSummaryAsJSONString = "";
		try {
			histogramSummaryAsJSONString = JSONUtils.getJSONStringFromGivenObject(histogramSummary);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		if (histogramSummaryAsJSONString.equals("") != true) {
			this.messagingTemplate.convertAndSend("/data", histogramSummaryAsJSONString);
		}

	}
}
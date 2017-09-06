package com.kafka.websocket.utils;

import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

@Component
public class DataGenerator implements ApplicationListener<BrokerAvailabilityEvent> {

	private final MessageSendingOperations<String> messagingTemplate;
	private final KafkaConsumer kafkaConsumer = new KafkaConsumer();
	private List<KafkaStream<byte[], byte[]>> streams = kafkaConsumer
			.getStreamsFromKafkaTopic("stream_processing_results");

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
	@Scheduled(fixedDelay = 1000)
	public void sendDataUpdates() {
		ConsumerIterator<byte[], byte[]> it = streams.get(0).iterator();
		String messageJSON = new String(it.next().message());
		System.out.println(messageJSON);
		JSONObject currentKafkaRecordJSONObject = JSONUtils.getJSONObjectFromGivenString(messageJSON);
		Object currentReadTagID = currentKafkaRecordJSONObject.get("readTag_id");
		Integer readTagID = Integer.valueOf(String.valueOf(currentReadTagID));
		//Hardcoded readTagID - application will be properly parametrized
		//TODO - as 'data' - json should be sended with informations like
		/*
		 * - readTagId - 
		 * - metadata information (location, tag description, name, unit - proper request to postgresql db should be made
		 * - value 
		 */
		Object currentMean = currentKafkaRecordJSONObject.get("mean");
		Double d = (Double) currentMean;
		Integer i = d.intValue(); // i becomes 5
		System.out.println(currentMean);
		// TODO - gets data from proper device id
		this.messagingTemplate.convertAndSend("/data", i);
			
		
	}
}
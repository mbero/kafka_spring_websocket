package com.kafka.websocket.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.common.record.Record;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaConsumer {
	final static String clientId = "SimpleConsumerDemoClient";
	final static String TOPIC = "bms";
	ConsumerConnector consumerConnector;

	/*
	 * public static void main(String[] argv) throws
	 * UnsupportedEncodingException { KafkaConsumer helloKafkaConsumer = new
	 * KafkaConsumer(); helloKafkaConsumer.run(); }
	 */

	public KafkaConsumer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "localhost:2181");
		properties.put("group.id", "test-group");
		properties.put("zookeeper.session.timeout.ms", "400");
		properties.put("zookeeper.sync.time.ms", "200");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("auto.offset.reset", "smallest");
		ConsumerConfig consumerConfig = new ConsumerConfig(properties);
		consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

	}

	public void run() {
		Map<String, Integer> topicCount = new HashMap<>();
		topicCount.put(TOPIC, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumerConnector.createMessageStreams(topicCount);
		List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(TOPIC);
		for (final KafkaStream stream : streams) {
			ConsumerIterator<byte[], byte[]> it = stream.iterator();
			while (it.hasNext()) {
				System.out.println("Message from Single Topic: " + new String(it.next().message()));
			}
		}
	}
	
	public List<KafkaStream<byte[], byte[]>> getStreamsFromKafkaTopic(String topicName){
		
		Map<String, Integer> topicCount = new HashMap<>();
		topicCount.put(topicName, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumerConnector.createMessageStreams(topicCount);
		List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topicName);
		
		return streams;
	}
}

package com.kafka.websocket.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestKafkaConsumer {
	
	
	@Test
	public void TestKafkaConsumer(){
		KafkaConsumer kafkaConsumer = new KafkaConsumer();
		kafkaConsumer.run();
	}
	
}

package com.test.example.apache.kafka.v9;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerTest {
	
	private static final String topic = "TRACE-TOPIC";
	
	private static final String topicGroup = topic + "-group";

	public static void main(String[] args) {
		Properties props = new Properties();
//		props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		 props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
		 "10.0.21.67:9092,10.0.21.68:9092,10.0.21.84:9092");
		props.put("group.id", topicGroup);
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		@SuppressWarnings("resource")
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList(topic));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
			}
			if (records != null && !records.isEmpty()) {
				System.out.println();
			}
		}
	}

}

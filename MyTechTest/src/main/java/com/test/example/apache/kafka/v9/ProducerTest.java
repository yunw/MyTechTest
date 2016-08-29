package com.test.example.apache.kafka.v9;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerTest {

	private static final String topic = "TRACE-TOPIC";

//	private static final Logger log = LoggerFactory.getLogger(ProducerTest.class);

	public static void main(String[] args) {

		int events = 5;
		Random rnd = new Random();

		Properties props = new Properties();

//		props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "10.0.21.67:9092,10.0.21.68:9092,10.0.21.84:9092");
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
		        "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		final CountDownLatch cdl = new CountDownLatch(events);
		for (long nEvents = 0; nEvents < events; nEvents++) {
			long runtime = new Date().getTime();
			String ip = "192.168.2." + rnd.nextInt(255);
			String msg = runtime + ", www.example.com, " + ip;
			System.out.println("sending topic: " + topic + ", ip: " + ip + ", msg: " + msg);
			ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, ip, msg);

			producer.send(data, new Callback() {
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					System.out.println("----sended ---");
					cdl.countDown();
				}
			});
		}
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		producer.close();
	}

}

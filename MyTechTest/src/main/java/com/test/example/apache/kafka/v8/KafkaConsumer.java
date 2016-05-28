package com.test.example.apache.kafka.v8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

public class KafkaConsumer {

	private final ConsumerConnector consumer;

	private KafkaConsumer() {
		Properties props = new Properties();
		// zookeeper 配置
		props.put("zookeeper.connect", "10.25.23.32:2181,10.25.23.33:2181,10.25.23.34:2181,10.25.23.35:2181,10.25.23.36:2181/kafka-dev");

		// group 代表一个消费组
		props.put("group.id", "jd-group");

		// zk连接超时
		props.put("zookeeper.session.timeout.ms", "4000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "smallest");
		// 序列化类
		props.put("serializer.class", "kafka.serializer.StringEncoder");

		ConsumerConfig config = new ConsumerConfig(props);

		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
	}

	private void consume() {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("newrvm", new Integer(1));

		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
		StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

		Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap,
				keyDecoder, valueDecoder);
		KafkaStream<String, String> stream = consumerMap.get("newrvm").get(0);
		ConsumerIterator<String, String> it = stream.iterator();
		while (it.hasNext()) {
			System.out.println("recive: " + it.next().message());
		}
	}

	public static void main(String[] args) {
		new KafkaConsumer().consume();
	}
}
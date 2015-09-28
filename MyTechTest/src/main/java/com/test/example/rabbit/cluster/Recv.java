package com.test.example.rabbit.cluster;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.test.example.rabbit.ConfigUtil;

public class Recv {

	private final static String QUEUE_NAME = "hello";
	
	private static boolean AUTO_ACK = true;

	public static void main(String[] argv) throws IOException, InterruptedException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
//		factory.setHost("10.25.23.39");
		factory.setPassword("admin");
		factory.setUsername("admin");
//		Connection connection = factory.newConnection();
		Connection connection = factory.newConnection(ConfigUtil.getAddresses());
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
			        byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, AUTO_ACK, consumer);
	}

}

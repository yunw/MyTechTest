package com.test.example.rabbit.publishsubscribe.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogs {
	
	private static final String EXCHANGE_NAME = "myExchange";
	private final static String QUEUE_NAME = "myQueue";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.25.23.242");
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setVirtualHost("/");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
	
//		String queueName = channel.queueDeclare().getQueue();
		String queueName = QUEUE_NAME;
		channel.queueBind(queueName, EXCHANGE_NAME, "foo.*");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
			        byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}

}
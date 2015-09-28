package com.test.example.rabbit.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.test.example.rabbit.ConfigUtil;

public class Send {
	
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
//		factory.setHost("10.25.23.39");
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setVirtualHost("/");
//		Connection connection = factory.newConnection();
		Connection connection = factory.newConnection(ConfigUtil.getAddresses());
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "Hello World!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		channel.close();
		connection.close();
	}
	
}

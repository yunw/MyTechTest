package com.test.example.apache.camel;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileMoveWithCamel {

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				// from("file:d:/temp/inbox?noop=true").to("file:d:/temp/outbox");
				from("file:d:/tmp/camel/inbox?delay=30000").to("file:d:/tmp/camel/outbox");
			}
		});
		context.start();
		TimeUnit.SECONDS.sleep(10);
		context.stop();
	}
}

package com.test.example.ws.jdk.rpc.client;

public class HelloWorldClient extends WebServiceClient {

	public HelloWorldClient(String url) {
		super(url);
	}
	
	public String getHelloWorldAsString(String s) throws Exception {
		return ws.getHelloWorld().getHelloWorldAsString(s);
	}
	
	public static void main(String[] args) throws Exception {
		HelloWorldClient client = new HelloWorldClient("http://localhost/TechTest/ws/");
		System.out.println(client.getHelloWorldAsString("aaaaaaa"));
	}
	
}

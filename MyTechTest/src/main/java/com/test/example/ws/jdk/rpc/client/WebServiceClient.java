package com.test.example.ws.jdk.rpc.client;

public class WebServiceClient {
	
	protected WebService ws;
	
	public WebServiceClient(String url) {
		ws = new WebService(url);
	}

}

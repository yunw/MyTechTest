package com.test.example.ws.jdk.rpc.service.impl;

import javax.jws.WebService;

import com.test.example.ws.jdk.rpc.service.HelloWorld;

//Service Implementation
@WebService(endpointInterface = "com.test.example.ws.jdk.rpc.service.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String getHelloWorldAsString(String name) {
		return "Hello World JAX-WS " + name;
	}

}

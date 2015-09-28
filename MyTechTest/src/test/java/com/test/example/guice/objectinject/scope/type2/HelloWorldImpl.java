package com.test.example.guice.objectinject.scope.type2;

import javax.inject.Singleton;

import com.test.example.guice.objectinject.HelloWorld;

@Singleton
public class HelloWorldImpl implements HelloWorld {

	public String sayHello() {
		return "Hello, world！type2。";
	}

}

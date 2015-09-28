package com.test.example.guice.objectinject.type5;

import com.google.inject.ImplementedBy;

@ImplementedBy(HelloWorldImpl.class)
public interface HelloWorld {
	String sayHello();
}

package com.test.example.guice.fieldinject;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class ConstructorInjectDemo {

	private Service service;
	
	public ConstructorInjectDemo() {
		System.out.println("call no arg constructor...");
		this.service = new ServiceImpl();
	}

	@Inject
	public ConstructorInjectDemo(Service service) {
		System.out.println("call args constructor...");
		this.service = service;
	}
	
	public static void main(String[] args) {
		ConstructorInjectDemo cid = Guice.createInjector().getInstance(
				ConstructorInjectDemo.class);
		System.out.println(cid.getService().execute());
	}

	@Test
	public void testConstructor() {
		ConstructorInjectDemo cid = Guice.createInjector().getInstance(
				ConstructorInjectDemo.class);
		System.out.println(cid.getService().execute());
	}

	public Service getService() {
		return service;
	}

}

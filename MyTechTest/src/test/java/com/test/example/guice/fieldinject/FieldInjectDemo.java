package com.test.example.guice.fieldinject;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class FieldInjectDemo {

	@Inject
	private Service service;

	public Service getService() {
		return service;
	}

	@Test
	public void testFieldInject() {
		FieldInjectDemo demo = Guice.createInjector().getInstance(
				FieldInjectDemo.class);
		Assert.assertEquals(demo.getService().execute(), "Hello world.");
	}

	@Test
	public void testFieldInject2() {
		FieldInjectDemo demo = new FieldInjectDemo();
		Guice.createInjector().injectMembers(demo);
		Assert.assertEquals(demo.getService().execute(), "Hello world.");
	}

}

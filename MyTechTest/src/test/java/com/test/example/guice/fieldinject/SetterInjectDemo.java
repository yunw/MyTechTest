package com.test.example.guice.fieldinject;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class SetterInjectDemo {

	private Service service;

	public static void main(String[] args) {
		SetterInjectDemo sid = Guice.createInjector().getInstance(
				SetterInjectDemo.class);
		sid.getService().execute();
	}
	
	@Test
	public void testSetterInject() {
		SetterInjectDemo sid = Guice.createInjector().getInstance(
				SetterInjectDemo.class);
		sid.getService().execute();
	}
	
	@Test
	public void testFieldInject2() {
		FieldInjectDemo demo = new FieldInjectDemo();
		Guice.createInjector().injectMembers(demo);
		Assert.assertEquals(demo.getService().execute(), "Hello world.");
	}

	public Service getService() {
		return service;
	}

	@Inject
	public void setService(Service service) {
		this.service = service;
	}

}

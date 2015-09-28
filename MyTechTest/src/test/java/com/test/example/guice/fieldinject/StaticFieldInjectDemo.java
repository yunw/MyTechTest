package com.test.example.guice.fieldinject;

import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

public class StaticFieldInjectDemo {

	@Inject
	private static Service service;

	public static void main(String[] args) {
		Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.requestStaticInjection(StaticFieldInjectDemo.class);
			}
		});
		StaticFieldInjectDemo.service.execute();
	}

	@Test
	public void testStaticFieldInject() {
		Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.requestStaticInjection(StaticFieldInjectDemo.class);
			}
		});
		StaticFieldInjectDemo.service.execute();
	}

}

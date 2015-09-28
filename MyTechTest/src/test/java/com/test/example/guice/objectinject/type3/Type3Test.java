package com.test.example.guice.objectinject.type3;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.test.example.guice.objectinject.HelloWorld;

/**
 * 由供应者来提供对象
 * 
 * @author Administrator
 *
 */
public class Type3Test {

	@Test
	public void sayHello() {
		Injector inj = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(HelloWorld.class).toProvider(
						new Provider<HelloWorld>() {
							public HelloWorld get() {
								return new HelloWorldImpl();
							}
						});
			}
		});
		HelloWorld hw = inj.getInstance(HelloWorld.class);
		Assert.assertEquals(hw.sayHello(), "Hello, world! type3.");
		HelloWorld hw2 = inj.getInstance(HelloWorld.class);
		Assert.assertEquals(hw2.sayHello(), "Hello, world! type3.");
		Assert.assertNotSame(hw, hw2);

	}
}

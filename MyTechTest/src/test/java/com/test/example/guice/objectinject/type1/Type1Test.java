package com.test.example.guice.objectinject.type1;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.test.example.guice.objectinject.HelloWorld;

/**
 * 将接口绑定到具体的类中，不是单例的，每次都会返回一个新的实例。
 * @author Administrator
 *
 */
public class Type1Test {

	@Test
	public void sayHello() {
		Injector inj = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(HelloWorld.class).to(HelloWorldImpl.class);
			}
		});
		HelloWorld hw = inj.getInstance(HelloWorld.class);
		Assert.assertEquals(hw.sayHello(), "Hello, world！type1。");
		HelloWorld hw2 = inj.getInstance(HelloWorld.class);
		Assert.assertEquals(hw2.sayHello(), "Hello, world！type1。");
		Assert.assertNotSame(hw, hw2);
		
	}
}

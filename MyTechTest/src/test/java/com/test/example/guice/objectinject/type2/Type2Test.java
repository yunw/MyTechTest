package com.test.example.guice.objectinject.type2;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * 绑定一个类的子类到本身，不是单例的，每次都会返回一个新的子类实例。
 * @author Administrator
 *
 */
public class Type2Test {

	@Test
	public void sayHello() {
		Injector inj = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(HelloWorldImpl.class).to(HelloWorldSubImpl.class);
			}
		});
		HelloWorldImpl hw = inj.getInstance(HelloWorldImpl.class);
		Assert.assertEquals(hw.sayHello(), "@HelloWorldSubImpl");
		HelloWorldImpl hw2 = inj.getInstance(HelloWorldImpl.class);
		Assert.assertNotSame(hw, hw2);
		
	}
}

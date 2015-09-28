package com.test.example.guice.objectinject.scope.type2;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.test.example.guice.objectinject.HelloWorld;

/**
 * 将接口绑定到具体的类中，通过注解指定获取实例的scope
 * 
 * @author Administrator
 *
 */
public class Type2Test {

	@Test
	public void sayHello() {
		Injector inj = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(HelloWorld.class).to(HelloWorldImpl.class)
						.in(Scopes.SINGLETON);
				;
			}
		});
		HelloWorld hw = inj.getInstance(HelloWorld.class);
		Assert.assertEquals(hw.sayHello(), "Hello, world！type2。");
		HelloWorld hw2 = inj.getInstance(HelloWorld.class);
		Assert.assertEquals(hw2.sayHello(), "Hello, world！type2。");
		Assert.assertSame(hw, hw2);

	}
}

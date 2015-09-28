package com.test.example.guice.objectinject.type4;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.test.example.guice.objectinject.HelloWorld;

/**
 * 实现类不绑定，由guice自动在classpath中扫描获取
 * 
 * @author Administrator
 *
 */
public class Type4Test {

	@Test
	public void sayHello() {
		Injector inj = Guice.createInjector();
		HelloWorld hw = inj.getInstance(HelloWorldImpl.class);
		Assert.assertEquals(hw.sayHello(), "Hello, world! type4.");
		HelloWorld hw2 = inj.getInstance(com.test.example.guice.objectinject.type3.HelloWorldImpl.class);
		Assert.assertEquals(hw2.sayHello(), "Hello, world! type3.");
		Assert.assertNotSame(hw, hw2);
	}
}

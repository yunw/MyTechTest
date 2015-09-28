package com.test.example.guice.objectinject.type5;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * 用注解的方式完成关联。在接口上指明此接口被哪个实现类关联。
 * 对于一个已经被注解的接口我们仍然可以使用Module来关联，这样获取的实例将是Module关联的实例，
 * 而不是@ImplementedBy注解关联的实例。这样仍然遵循一个原则，手动优于自动。
 * 
 * @author Administrator
 *
 */
public class Type5Test {

	@Test
	public void sayHello() {
		Injector inj = Guice.createInjector();
		HelloWorld hw = inj.getInstance(HelloWorld.class);
		Assert.assertEquals(hw.sayHello(), "Hello, world! type5.");
	}

	public void syaHello2() {
		Injector inj = Guice.createInjector(new Module() {

			@Override
			public void configure(Binder binder) {
				binder.bind(HelloWorld.class).to(HelloWorldImplAgain.class);
			}
		});
		HelloWorld hw = inj.getInstance(HelloWorld.class);
		Assert.assertEquals(hw.sayHello(), "Hello, world again! type5.");
	}
}

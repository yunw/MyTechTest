package com.test.example.guice.fieldinject.multibind;

import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

public class StaticMultiInterfaceServiceDemo {

	@Inject
	@Www
	private static Service2 wwwService;
	@Inject
	@Home
	private static Service2 homeService;

	@Test
	public void testMultiInterface() {
		Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(Service2.class).annotatedWith(Www.class)
						.to(WwwService.class);
				binder.bind(Service2.class).annotatedWith(Home.class)
						.to(HomeService.class);
				binder.requestStaticInjection(StaticMultiInterfaceServiceDemo.class);
			}
		});
		StaticMultiInterfaceServiceDemo.homeService.execute();
		StaticMultiInterfaceServiceDemo.wwwService.execute();
	}

}

package com.test.example.guice.fieldinject.multibind;

import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

public class MultiInterfaceServiceDemo {

	@Inject
	@Www
	private Service2 wwwService;
	@Inject
	@Home
	private Service2 homeService;

	@Test
	public void testMultiInterface() {
		Injector injector = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(Service2.class).annotatedWith(Www.class)
						.to(WwwService.class);
				binder.bind(Service2.class).annotatedWith(Home.class)
						.to(HomeService.class);
			}
		});
		MultiInterfaceServiceDemo misd = injector
				.getInstance(MultiInterfaceServiceDemo.class);
		misd.homeService.execute();
		misd.wwwService.execute();
	}

}

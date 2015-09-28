package com.test.example.guice.fieldinject.multibind;

import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class NoAnnotationMultiInterfaceServiceDemo {

	@Inject
	@Named("Www")
	private static Service2 wwwService;
	@Inject
	@Named("Home")
	private static Service2 homeService;

	@Test
	public  void test() {
		Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(Service2.class).annotatedWith(Names.named("Www"))
						.to(WwwService.class);
				binder.bind(Service2.class).annotatedWith(Names.named("Home"))
						.to(HomeService.class);
				binder.requestStaticInjection(NoAnnotationMultiInterfaceServiceDemo.class);
			}
		});
		NoAnnotationMultiInterfaceServiceDemo.homeService.execute();
		NoAnnotationMultiInterfaceServiceDemo.wwwService.execute();
	}

}

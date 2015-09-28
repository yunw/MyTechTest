package com.test.example.guice.fieldinject;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceImpl.class)
public interface Service {

	String execute();
	
}

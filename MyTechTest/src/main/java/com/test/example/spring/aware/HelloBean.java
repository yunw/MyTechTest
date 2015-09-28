package com.test.example.spring.aware;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HelloBean implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private String helloWord = "Hello!World!";

	public void setApplicationContext(ApplicationContext context) {
		this.applicationContext = context;
	}

	public void setHelloWord(String helloWord) {
		this.helloWord = helloWord;
	}

	public String getHelloWord() {
		applicationContext.publishEvent(new PropertyGettedEvent("[" + helloWord + "] is getted"));
		return helloWord;
	}

}

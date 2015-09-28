package com.test.example.spring.aware;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	@SuppressWarnings("resource")
    public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		HelloBean hello = (HelloBean) context.getBean("helloBean");
		HelloBean2 hello2 = (HelloBean2) context.getBean("helloBean2");
		System.out.println(hello.getHelloWord());
		System.out.println(hello2.getHelloWord());
	}

}

package com.test.example.ws.jdk.rpc.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.test.example.ws.jdk.rpc.service.HelloWorld;
import com.test.example.ws.jdk.rpc.service.UserInfo;

public class WebService {
	
	String url = "";
	
	public WebService(String url) {
		this.url = url;
	}
	
	public UserInfo getUserInfo() throws Exception {
		QName qname = new QName("http://impl.service.rpc.jdk.ws.example.test.com/",
				"UserInfoImplService");
		Service service = Service.create(new URL(url + "user?wsdl"), qname);
		return service.getPort(UserInfo.class);
	}
	
	public HelloWorld getHelloWorld() throws Exception {
		QName qname = new QName("http://impl.service.rpc.jdk.ws.example.test.com/",
				"HelloWorldImplService");
		Service service = Service.create(new URL(url + "hello?wsdl"), qname);
		return service.getPort(HelloWorld.class);
	}

}

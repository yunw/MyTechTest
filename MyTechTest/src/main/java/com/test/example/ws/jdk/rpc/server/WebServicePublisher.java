package com.test.example.ws.jdk.rpc.server;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.xml.ws.Endpoint;

import com.test.example.ws.jdk.rpc.service.impl.HelloWorldImpl;
import com.test.example.ws.jdk.rpc.service.impl.UserInfoImpl;

@SuppressWarnings("serial")
public class WebServicePublisher extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws javax.servlet.ServletException {
		String wsurl = config.getServletContext().getInitParameter("wsurl");
		hello(wsurl);
		user(wsurl);
	}
	
//	@Override
//	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		System.out.println("------------------------------");
//		System.out.println("local addr: " + req.getLocalAddr());
//		System.out.println("local name: " + req.getLocalName());
//		System.out.println("path info: " + req.getPathInfo());
//		System.out.println("server name: " + req.getServerName());
//		System.out.println("server port: " + req.getServerPort());
//		System.out.println("protocol: " + req.getProtocol());
//		System.out.println("servlet path: " + req.getServletPath());
//		System.out.println("------------------------------");
//	}
	
//	public static void main(String[] args) {
//		hello();
//		user();
//	}
	
	public static void hello(String url) {
		   Endpoint.publish(url + "hello", new HelloWorldImpl());
	}
	
	public static void user(String url) {
		   Endpoint.publish(url + "user", new UserInfoImpl());
	}

}

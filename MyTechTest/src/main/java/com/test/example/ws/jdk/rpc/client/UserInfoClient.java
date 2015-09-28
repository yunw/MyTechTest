package com.test.example.ws.jdk.rpc.client;

import com.test.example.ws.jdk.rpc.model.User;

public class UserInfoClient extends WebServiceClient {

	public UserInfoClient(String url) {
		super(url);
	}
	
	public String getUserName(String s) throws Exception {
		return ws.getUserInfo().getUserName(s);
	}
	
	public User getUser() throws Exception {
		return ws.getUserInfo().getUser();
	}
	
	public static void main(String[] args) throws Exception {
		UserInfoClient client = new UserInfoClient("http://localhost/TechTest/ws/");
		System.out.println(client.getUserName("bbb"));
		User user = client.getUser();
		System.out.println(user.getAddr());
	}
	
}

package com.test.example.ws.jdk.rpc.service.impl;

import java.util.Date;

import javax.jws.WebService;

import com.test.example.ws.jdk.rpc.model.User;
import com.test.example.ws.jdk.rpc.service.UserInfo;

//Service Implementation
@WebService(endpointInterface = "com.test.example.ws.jdk.rpc.service.UserInfo")
public class UserInfoImpl implements UserInfo {

	@Override
	public String getUserName(String name) {
		return "Your name is: " + name;
	}
	
	public User getUser() {
		User user = new User();
		user.setAddr("shanghai str");
		user.setBirthDay(new Date());
		user.setName("hai");
		return user;
	}

}

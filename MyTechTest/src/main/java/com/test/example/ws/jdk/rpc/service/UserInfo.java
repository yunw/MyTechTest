package com.test.example.ws.jdk.rpc.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.test.example.ws.jdk.rpc.model.User;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface UserInfo {

	@WebMethod
	String getUserName(String name);
	
	@WebMethod
	User getUser();
	
}

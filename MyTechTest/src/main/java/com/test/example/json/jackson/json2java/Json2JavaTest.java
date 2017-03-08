package com.test.example.json.jackson.json2java;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json2JavaTest {

	private static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {
//		printUser();
//		printlnUserList();
		
		// 集合 对象 集合 转换
				String josn4 = "[{\"name\":\"菠萝大象\",\"gender\":\"MALE\",\"accounts\":[{\"id\":1,\"cardId\":\"423335533434\",\"balance\":1900.2,\"date\":1486547536286},{\"id\":2,\"cardId\":\"625444548433\",\"balance\":5000,\"date\":1486547536286}]},{\"name\":\"菠萝大象2\",\"gender\":\"MALE\",\"accounts\":[{\"id\":1,\"cardId\":\"12345\",\"balance\":1900.2,\"date\":1486547536286},{\"id\":2,\"cardId\":\"45678\",\"balance\":5000,\"date\":1486547536286}]}]";
				JavaType javaType4 = mapper.getTypeFactory().constructParametricType(
						List.class, User.class);
				List<User> list = mapper.readValue(josn4, javaType4);
				System.out.println("集合里是对象 对象里有集合转换:" + list);

	}

	private static void printUser() throws IOException {
		User user = new User();

		user.setName("菠萝大象");
		user.setGender(Gender.MALE);

		List<Account> accounts = new ArrayList<Account>();

		Account account = new Account();
		account.setId(1);
		account.setBalance(BigDecimal.valueOf(1900.2));
		account.setCardId("423335533434");
		account.setDate(new Date());
		accounts.add(account);

		account = new Account();
		account.setId(2);
		account.setBalance(BigDecimal.valueOf(5000));
		account.setCardId("625444548433");
		account.setDate(new Date());
		accounts.add(account);

		user.setAccounts(accounts);

		String json = mapper.writeValueAsString(user);

		System.out.println("Java2Json: " + json);

		user = mapper.readValue(json, User.class);

		System.out.println("Json2Java: " + mapper.writeValueAsString(user));
	}
	
	private static void printlnUserList() throws JsonProcessingException {
		User user = new User();

		user.setName("菠萝大象");
		user.setGender(Gender.MALE);

		List<Account> accounts = new ArrayList<Account>();

		Account account = new Account();
		account.setId(1);
		account.setBalance(BigDecimal.valueOf(1900.2));
		account.setCardId("423335533434");
		account.setDate(new Date());
		accounts.add(account);

		account = new Account();
		account.setId(2);
		account.setBalance(BigDecimal.valueOf(5000));
		account.setCardId("625444548433");
		account.setDate(new Date());
		accounts.add(account);

		user.setAccounts(accounts);
		
		User user2 = new User();

		user2.setName("菠萝大象2");
		user2.setGender(Gender.MALE);

		List<Account> accounts2 = new ArrayList<Account>();

		Account account2 = new Account();
		account2.setId(1);
		account2.setBalance(BigDecimal.valueOf(1900.2));
		account2.setCardId("12345");
		account2.setDate(new Date());
		accounts2.add(account2);

		account2 = new Account();
		account2.setId(2);
		account2.setBalance(BigDecimal.valueOf(5000));
		account2.setCardId("45678");
		account2.setDate(new Date());
		accounts2.add(account2);

		user2.setAccounts(accounts2);
		
		List<User> userList = new ArrayList<>();
		userList.add(user);
		userList.add(user2);
		
		String json = mapper.writeValueAsString(userList);

		System.out.println("Java2Json: " + json);
		
	}

	/**
	 * 转集合
	 * 
	 * @param josn
	 * @param clz
	 * @return
	 */
	public List<T> jsonConverList(String josn, Class<T> clz) {

		List<T> me = null;
		try {

			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clz);

			me = mapper.readValue(josn, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return me;
	}
}

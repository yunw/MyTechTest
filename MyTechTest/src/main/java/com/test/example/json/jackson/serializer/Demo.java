package com.test.example.json.jackson.serializer;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.htrace.fasterxml.jackson.core.JsonGenerationException;
import org.apache.htrace.fasterxml.jackson.core.JsonParseException;
import org.apache.htrace.fasterxml.jackson.databind.JsonMappingException;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

public class Demo {
	
	private static String fileName = "d:/person.json";
	public static void main(String[] args) {

//		writeJsonObject();

		 readJsonObject();
	}

	// 直接写入一个对象(所谓序列化)
	public static void writeJsonObject() {
		ObjectMapper mapper = new ObjectMapper();
		Person person = new Person("nomouse", 25, true, new Date(), "程序员", 2500.10);
		try {
			mapper.writeValue(new File(fileName), person);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 直接将一个json转化为对象（所谓反序列化）
	public static void readJsonObject() {
		ObjectMapper mapper = new ObjectMapper();

		try {
			Person person = mapper.readValue(new File(fileName), Person.class);
			System.out.println(person.toString());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

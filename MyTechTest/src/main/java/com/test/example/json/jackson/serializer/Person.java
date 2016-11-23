package com.test.example.json.jackson.serializer;

import java.util.Date;

import org.apache.htrace.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.htrace.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.htrace.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(value = { "word" })
public class Person {
	private String name;
	private int age;
	private boolean sex;
	private Date birthday;
	private String word;
	private double salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	// 反序列化一个固定格式的Date
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	// 序列化指定格式的double格式
	@JsonSerialize(using = CustomDoubleSerializer.class)
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Person(String name, int age, boolean sex, Date birthday, String word, double salary) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.birthday = birthday;
		this.word = word;
		this.salary = salary;
	}

	public Person() {
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", sex=" + sex + ", birthday=" + birthday + ", word=" + word
				+ ", salary=" + salary + "]";
	}

}

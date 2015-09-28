package com.test.example.base.jdk8;

//@Hint("hint1")
//@Hint("hint2")
public class Person {
	
	String firstName;
	
	String lastName;
	
	public Integer getNameLength(String name) {
		return name.length();
	}

	public Person() {
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}

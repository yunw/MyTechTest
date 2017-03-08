package com.test.example.json.jackson.json2java;

import java.util.List;

import lombok.Data;

@Data
public class User {
	
	private String name;

    private Gender gender;

    private List<Account> accounts;
    
}

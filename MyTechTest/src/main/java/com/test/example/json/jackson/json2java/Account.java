package com.test.example.json.jackson.json2java;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Account {

	private Integer id;

	private String cardId;

	private BigDecimal balance;

	private Date date;

}

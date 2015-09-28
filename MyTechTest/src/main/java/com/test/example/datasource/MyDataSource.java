package com.test.example.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class MyDataSource {

	private static BasicDataSource bds = null;

	static {
		bds = new BasicDataSource();
		bds.setDefaultAutoCommit(false);
		bds.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl("jdbc:mysql://127.0.0.1:3306/mysql");
		bds.setUsername("root");
	}
	
	public static DataSource getDataSource() {
		return bds;
	}
	
	public static Connection getConnection() {
		try {
			return bds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

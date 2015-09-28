package com.test.example.datasource.neted;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.test.example.datasource.MyDataSource;

public class Test {
	
	public static void main(String[] args) {
		Test test = new Test();
		test.test();
	}

	public void test() {
		Connection conn = MyDataSource.getConnection();
		PreparedStatement stat = null;
		try {
			conn.setReadOnly(false);
			String sql = "insert into t_user(id, name, version) values(4, 'test', 1)";
			stat = conn.prepareStatement(sql);
			stat.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

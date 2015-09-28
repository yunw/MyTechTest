package com.test.example.ipparse;

import junit.framework.TestCase;

public class IPtest extends TestCase {

	public void testIp() {
		// 指定纯真数据库的文件名，所在文件夹
		IPSeeker ip = new IPSeeker("QQWry.Dat", "C:\\Program Files\\cz88.net\\ip");
		// 测试IP 58.20.43.13
		System.out.println(ip.getIPLocation("180.170.130.36").getCountry() + ":"
		        + ip.getIPLocation("180.170.130.36").getArea());
	}
	
}

package com.test.example.git;

import com.googlecode.openbox.server.ssh.LinuxClient;

public class LinuxTest {
	
	public static void main(String[] args) {
//		LinuxClient client = new LinuxClient("10.25.23.39", 22, "root", "Root1q2w");
		LinuxClient client = new LinuxClient("10.25.23.165", 22, "root", "Pass1234");
		String s = client.executeCommand("python /paas/k8s/python/test2.py");
		System.out.println(s);
		client.close();
	}

}

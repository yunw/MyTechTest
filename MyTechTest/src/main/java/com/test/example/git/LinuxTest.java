package com.test.example.git;

import com.googlecode.openbox.server.ssh.LinuxClient;

public class LinuxTest {
	
	public static void main(String[] args) {
		LinuxClient client = new LinuxClient("10.25.23.39", 22, "root", "Root1q2w");
		String s = client.executeCommand("/data/git/test.sh aaa");
		System.out.println(s);
		client.close();
	}

}

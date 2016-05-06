package com.test.example.git;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.googlecode.openbox.server.ssh.CommonSshClient;
import com.googlecode.openbox.server.ssh.SshClient;

public class SSHTest {

	public static void main(String[] args) throws FileNotFoundException {
		SshClient sc = new CommonSshClient("10.25.23.39", 22, "root", "Root1q2w");
		
		OutputStream os = new FileOutputStream("D:/test/test.txt");
		sc.executeShell(os, "/data/git/test.sh aaa");
	}

}

package com.test.example.network;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.googlecode.openbox.server.ssh.LinuxClient;

public class RemoteCallJenkins {

	public static void main(String[] args) {
		runSsh(args);
	}

	@SuppressWarnings("unchecked")
	public static void runSsh(String[] args) {
		Properties props = new Properties();
		String fileName = RemoteCallJenkins.class.getResource("/deploy").getPath() + "/deploy.properties";
		try {
			props.load(new BufferedInputStream(new FileInputStream(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		LinuxClient client = new LinuxClient(props.getProperty("ip"), Integer.parseInt(props.getProperty("port")),
				props.getProperty("userName"), props.getProperty("password"));
		Enumeration<String> enumeration = (Enumeration<String>) props.propertyNames();
		Set<String> keys = new HashSet<String>();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			if (key.startsWith("cmd")) {
				keys.add(key);
			}
		}
		for (String key : keys) {
			String cmd = props.getProperty(key);
			if (args != null) {
				for (String arg : args) {
					cmd += " " + arg;
				}
//				cmd += " \nsleep 30";
			}
			String s = client.executeCommand(cmd);
			System.out.println(s);
		}

		client.close();
	}

}

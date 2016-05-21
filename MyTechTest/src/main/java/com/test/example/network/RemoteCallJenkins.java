package com.test.example.network;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.dom4j.DocumentException;

import com.googlecode.openbox.server.ssh.LinuxClient;

public class RemoteCallJenkins {

	private static Properties props = new Properties();

	static {
		String fileName = RemoteCallJenkins.class.getResource("/deploy").getPath() + "/deploy.properties";
		try {
			props.load(new BufferedInputStream(new FileInputStream(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws MalformedURLException, DocumentException {
//		 remoteDeploy(args);
		 jobStatus();
	}

	public static void jobStatus() {
		LinuxClient client = getLinuxClient();
		String cmd = props.getProperty("jobStatus");
		String s = client.executeCommand(cmd);
		System.out.println(s);
		client.close();
	}

	@SuppressWarnings("unchecked")
	public static void remoteDeploy(String[] args) {
		LinuxClient client = getLinuxClient();
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
			}
			String s = client.executeCommand(cmd);
			System.out.println(s);
		}

		client.close();
	}

	private static LinuxClient getLinuxClient() {
		return new LinuxClient(props.getProperty("ip"), Integer.parseInt(props.getProperty("port")),
				props.getProperty("userName"), props.getProperty("password"));
	}

}

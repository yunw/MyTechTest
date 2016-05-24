package com.test.example.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSONObject;
import com.googlecode.openbox.server.ssh.LinuxClient;

import net.iharder.base64.Base64;

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

	public static void main(String[] args) throws MalformedURLException, DocumentException, InterruptedException {
//		 remoteDeploy(args);
		 jobStatus();
	}

	public static void jobStatus() {
		try {
			URL url = new URL("http://10.25.20.104:8080/job/tracereport/lastBuild/api/json?tree=result");
			String encoding = Base64.encodeBytes(("abfme:Pass1234").getBytes());

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
			JSONObject jsonObj = JSONObject.parseObject(sb.toString());
			System.out.println(jsonObj.toJSONString());
			System.out.println(jsonObj.getString("result"));
			in.close();
			content.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

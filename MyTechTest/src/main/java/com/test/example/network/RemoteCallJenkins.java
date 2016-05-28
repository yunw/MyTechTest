package com.test.example.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

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
//		remoteDeploy();
//		remoteDeploy2();
		test2();
//		 jobStatus();
	}

	public static void jobStatus() {
		try {
			URL url = new URL("http://10.25.20.104:8080/job/common/lastBuild/api/json");
			String encoding = Base64.encodeBytes(("abfme:fd765a383e77efc40c0ddefee17fc827").getBytes());

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

	public static void remoteDeploy() throws InterruptedException {
		LinuxClient client = getLinuxClient();
		String s = client.executeCommand(props.getProperty("cmd0"));
		System.out.println(s);
		client.close();
	}
	
	public static void test2() throws InterruptedException {
		
		HttpURLConnection connection = null;
				
		try {
			URL url = new URL("http://10.25.20.104:8080/job/common/buildWithParameters?token=remote-build-token&projectName=tracereport&gitBranch=zxq_dev");
			String encoding = Base64.encodeBytes(("abfme:fd765a383e77efc40c0ddefee17fc827").getBytes());

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			
			 System.out.println(connection.getResponseCode());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public static void test1() {
		
		HttpURLConnection connection = null;
		InputStream content = null;
		BufferedReader in = null;
		try {
			URL url = new URL("http://10.25.20.105:8080/job/common/lastBuild/api/json");
			String encoding = Base64.encodeBytes(("admin:fd765a383e77efc40c0ddefee17fc829").getBytes());

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			content = (InputStream) connection.getInputStream();
			in = new BufferedReader(new InputStreamReader(content));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public static void remoteDeploy2() throws InterruptedException {
		try {
			URL url = new URL("http://10.25.20.104:8080/job/common/build");
			String encoding = Base64.encodeBytes(("abfme:fd765a383e77efc40c0ddefee17fc827").getBytes());

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			
			 StringBuffer params = new StringBuffer();
			 params.append("json='{\"parameter\":[{\"name\":\"projectName\",\"value\":\"tracereport\"},{\"name\":\"gitBranch\",\"value\":\"zxq_dev\"}]}'&token=remote-build-token");
//			 HttpResponse<String> response = Unirest.post("http://10.25.20.104:8080/job/common/build")
//					  .body(params)
//					  .asString();
      byte[] bypes = params.toString().getBytes();
      connection.getOutputStream().write(bypes);// 输入参数
      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      }
      System.out.println(connection.getResponseCode());
//			InputStream content = (InputStream) connection.getInputStream();
//			BufferedReader in = new BufferedReader(new InputStreamReader(content));
//			String line;
//			StringBuilder sb = new StringBuilder();
//			while ((line = in.readLine()) != null) {
//				sb.append(line);
//			}
//			System.out.println(sb.toString());
//			JSONObject jsonObj = JSONObject.parseObject(sb.toString());
//			System.out.println(jsonObj.toJSONString());
//			System.out.println(jsonObj.getString("result"));
//			in.close();
//			content.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static LinuxClient getLinuxClient() {
//		return new LinuxClient("10.25.20.104", 8080, "abfme", "fd765a383e77efc40c0ddefee17fc827");

		String ip = props.getProperty("ip");
		int port = Integer.parseInt(props.getProperty("port"));
		String username = props.getProperty("userName");String password = props.getProperty("password");
		System.out.println("ip: " + ip + ", port: " + port + ", username: " + username + ", password: " + password);
		return new LinuxClient(ip,
		 port,
		 username, password);
	}

}

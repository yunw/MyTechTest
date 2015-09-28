package com.test.example.proxy;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class JavaProxy {

	private static String proxyHost = "10.90.1.233";

	private static Integer proxyPort = 80;

	public static void main(String[] args) throws Exception {
		socketProxy();
		httpProxy();
	}
	
	private static void socketProxy()  throws Exception {
		Socket socket = new Socket(proxyHost, proxyPort);
		socket.getOutputStream().write(new String("GET https://api.weixin.qq.com HTTP/1.1\r\n\r\n").getBytes());
		byte[] bytes = new byte[1024];
		InputStream is = socket.getInputStream();
		int i = 0;
		while((i =is.read(bytes)) > 0) {
			System.out.println(new String(bytes, 0, i));
		}
		is.close();
		socket.close();
	}
	
	private static void httpProxy() throws Exception {
		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", String.valueOf(proxyPort));
		URL url = new URL("https://api.weixin.qq.com");
		URLConnection conn = url.openConnection();
		InputStreamReader isr = new InputStreamReader(conn.getInputStream());
		char[] chars = new char[1024];
		int i = 0;
		while((i = isr.read(chars)) > 0) {
			System.out.println(new String(chars, 0, i));
		}
		isr.close();
	}

}

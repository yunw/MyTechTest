package com.test.example.security.ssl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

public class SSLServer extends Thread {
	
	private static String SERVER_KEY_STORE = "d:/ssl/server_ks";
	
	private static String SERVER_KEY_STORE_PASSWORD = "123123";
	
	private Socket socket;

	public SSLServer(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			System.out.println("Server is listening....");
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream());

			String data = reader.readLine();
			System.out.println(data);
			writer.println("from server: ............................");
			writer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("server......................");
		System.setProperty("javax.net.ssl.trustStore", SERVER_KEY_STORE);
		SSLContext context = SSLContext.getInstance("TLS");

		KeyStore ks = KeyStore.getInstance("jceks");
		ks.load(new FileInputStream(SERVER_KEY_STORE), null);
		KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
		kf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());

		context.init(kf.getKeyManagers(), null, null);

		ServerSocketFactory factory = context.getServerSocketFactory();
		//默认的https端口号是443，因此tomcat为了区别使用了8443作为https的端口号。
		ServerSocket _socket = factory.createServerSocket(8443);
		
		//不需要客户端认证
		//((SSLServerSocket) _socket).setNeedClientAuth(false);

		//需要客户端认证，因此客户端也要提供数字证书
		((SSLServerSocket) _socket).setNeedClientAuth(true);
		
		new SSLServer(_socket.accept()).start();

	}
}

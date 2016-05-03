package com.test.example.security.ssl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {

	private static String CLIENT_KEY_STORE = "d:/ssl/client_ks";
	
	private static String CLIENT_KEY_STORE_PASSWORD = "456456";
	
	private static String common_name = "test.com";
	
	private static int ssl_port = 8443;

	public static void main(String[] args) throws Exception {
		// 设置信任证书仓库，只有在该仓库中的证书才是受信任的
		System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);

		//设置网络通信的日志级别为debug，显示ssl连接握手的信息
		System.setProperty("javax.net.debug", "ssl,handshake");

		SSLClient client = new SSLClient();
		
//		Socket s = client.clientWithoutCert();
		
		Socket s = client.clientWithCert();

		PrintWriter writer = new PrintWriter(s.getOutputStream());
		writer.println("from client..........................");
		writer.flush();
		BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println(reader.readLine());
		s.close();
	}

	/**
	 * 客户端不提供证书，因此是单向握手
	 * @return
	 * @throws Exception
	 */
	private Socket clientWithoutCert() throws Exception {
		SocketFactory sf = SSLSocketFactory.getDefault();
		//服务端证书的CN（common name）被设定为test.com那么客户端在连接服务端的时候，
		//也要用这个域名（可在hosts文件中配置）来连接，否则根据SSL协议标准，域名与证书的CN不匹配，
		//说明这个证书是不安全的，通信将无法正常运行。
		Socket s = sf.createSocket(common_name, ssl_port);
		return s;
	}
	
	/**
	 * 客户端也要提供证书，因此是双向握手
	 * @return
	 * @throws Exception
	 */
	private Socket clientWithCert() throws Exception {  
        SSLContext context = SSLContext.getInstance("TLS");  
        KeyStore ks = KeyStore.getInstance("jceks");
          
        ks.load(new FileInputStream(CLIENT_KEY_STORE), null);  
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");  
        kf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());  
        context.init(kf.getKeyManagers(), null, null);  
          
        SocketFactory factory = context.getSocketFactory();  
        Socket s = factory.createSocket(common_name, ssl_port);  
        return s;  
    } 
}

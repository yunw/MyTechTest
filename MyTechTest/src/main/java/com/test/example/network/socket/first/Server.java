package com.test.example.network.socket.first;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String args[]) {
		// 为了简单起见，所有的异常信息都往外抛
		int port = 8899;
		try (
				// 定义一个ServerSocket监听在端口8899上
				ServerSocket server = new ServerSocket(port);
				// server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
				Socket socket = server.accept();
				// 跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。
				Reader reader = new InputStreamReader(socket.getInputStream());) {
			char chars[] = new char[64];
			int len;
			StringBuilder sb = new StringBuilder();
			while ((len = reader.read(chars)) != -1) {
				sb.append(new String(chars, 0, len));
			}
			System.out.println("from client: " + sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.test.example.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	static final int MESSAGE_LENGTH_HEAD = 4;

	public static void main(String args[]) {
		try {
			Socket socket = new Socket("127.0.0.1", 4700);
			// 向本机的4700端口发出客户请求
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			// 从系统标准输入读入一字符串
			String readline = sin.readLine();
			// 若从标准输入读入的字符串为 "bye"则停止循环
			while (!readline.equals("bye")) {

				writesMessage(readline, socket);
				// 刷新输出流，使Server马上收到该字符串
				System.out.println("Client:" + readline);
				// 在系统标准输出上打印读入的字符串
				readsMessage(socket);
				// 从Server读入一字符串，并打印到标准输出上
				readline = sin.readLine(); // 从系统标准输入读入一字符串
			} // 继续循环
			socket.close(); // 关闭Socket
		} catch (Exception e) {
			System.out.println("Error" + e); // 出错，则打印出错信息
		}
	}

	private static void writesMessage(String messages, Socket socket) {
		try {
			byte[] bytes = messages.getBytes("utf-8");
			DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
			// 编写数据的长度
			dataOutput.writeInt(bytes.length);
			dataOutput.write(bytes);
			dataOutput.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readsMessage(Socket socket) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String s = in.readLine();
					if (null != s) {
						System.out.println(s);
						s = in.readLine();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();

	}

}
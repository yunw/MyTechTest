package com.test.example.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Bytes2util {

	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public static void outputHex(byte[] data, int ary) {
		try {
			System.out.println(new String(data, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeResponse(SocketChannel socket, byte[] data) {
		
		 ByteBuffer sendBuffer=ByteBuffer.wrap(data);
		try {
//			System.out.println("server messages: " + new String(sendBuffer.array(), "utf-8"));
			socket.write(sendBuffer);
			socket.write(ByteBuffer.wrap("".getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

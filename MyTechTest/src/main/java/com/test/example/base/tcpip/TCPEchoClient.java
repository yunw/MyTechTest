package com.test.example.base.tcpip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

public class TCPEchoClient {

	public static void main(String[] args) throws IOException {

		SocketAddress socketAddress = test(args);
		
		Socket s2 = new Socket();
		
		s2.connect(socketAddress);
		
		t1(args[1].getBytes(), s2);
		
	}
	
	public static SocketAddress test(String[] args) throws IOException {
		if ((args.length < 2) || (args.length > 3)) // Test for correct # of args
			throw new IllegalArgumentException(
					"Parameter(s): <Server> <Word> [<Port>]");

		String server = "localhost"; // Server name or IP address
		// Convert argument String to bytes using the default character encoding
		byte[] data = args[1].getBytes();

		int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;
		// Create socket that is connected to server on specified port
		Socket socket = new Socket(server, servPort);
		t1(data, socket);
		
		SocketAddress socketAddress = socket.getRemoteSocketAddress();
		System.out.println(((InetSocketAddress)socket.getLocalSocketAddress()).getPort());

		socket.close(); // Close the socket and its streams
		return socketAddress;
	}
	
	public static void t1(byte[] data, Socket socket) throws IOException {
		System.out.println("Connected to server...sending echo string");

		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();

		out.write(data); // Send the encoded string to the server

		// Receive the same string back from the server

		int totalBytesRcvd = 0; // Total bytes received so far
		int bytesRcvd; // Bytes received in last read
		while (totalBytesRcvd < data.length) {
			if ((bytesRcvd = in.read(data, totalBytesRcvd, data.length
					- totalBytesRcvd)) == -1)
				throw new SocketException("Connection closed prematurely");
			totalBytesRcvd += bytesRcvd;
		} // data array is full

		System.out.println("Received: " + new String(data));
		
		socket.close(); // Close the socket and its streams
	}
}
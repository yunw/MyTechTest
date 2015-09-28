package com.test.example.base.tcpip;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNonblocking {

	public static void main(String args[]) throws Exception {

		// Test for correct # of args
		if ((args.length < 2) || (args.length > 3))
			throw new IllegalArgumentException(
					"Parameter(s): <Server> <Word> [<Port>]");

		String server = args[0]; // Server name or IP address
		// Convert input String to bytes using the default charset
		byte[] argument = args[1].getBytes();
		int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

		// Create channel and set to nonblocking
		SocketChannel clntChan = SocketChannel.open();
		clntChan.configureBlocking(false);

		// Initiate connection to server and repeatedly poll until complete
		// 由于该套接字是非阻塞式的，因此对connect()方法的调用可能会在连接建立之前返回，
		// 如果在返回前已经成功建立了连接，则返回true，否则返回false。对于后一种情况，
		// 任何试图发送或接收数据的操作都将抛出NotYetConnectedException异常，
		// 因此，我们通过持续调用finishConnect()方法来"轮询"连接状态，该方法在连接成功建立之前一直返回false。
		// 打印操作演示了在等待连接建立的过程中，程序还可以执行其他任务
		if (!clntChan.connect(new InetSocketAddress(server, servPort))) {
			while (!clntChan.finishConnect()) {
				System.out.print("."); // Do something else
			}
		}
		ByteBuffer writeBuf = ByteBuffer.wrap(argument);
		ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
		int totalBytesRcvd = 0; // Total bytes received so far
		int bytesRcvd; // Bytes received in last read
		while (totalBytesRcvd < argument.length) {
			if (writeBuf.hasRemaining()) {
				clntChan.write(writeBuf);
			}
			if ((bytesRcvd = clntChan.read(readBuf)) == -1) {
				throw new SocketException("Connection closed prematurely");
			}
			totalBytesRcvd += bytesRcvd;
			System.out.print("."); // Do something else
		}

		System.out.println("Received: " + // convert to String per default
											// charset
				new String(readBuf.array(), 0, totalBytesRcvd));
		clntChan.close();
	}
}
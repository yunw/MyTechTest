package com.test.example.base.tcpip.vote.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import com.test.example.base.tcpip.Framer;
import com.test.example.base.tcpip.LengthFramer;
import com.test.example.base.tcpip.vote.VoteMsg;
import com.test.example.base.tcpip.vote.VoteMsgBinCoder;
import com.test.example.base.tcpip.vote.VoteMsgCoder;
import com.test.example.base.tcpip.vote.VoteService;

public class VoteServerTCP {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port>");
		}

		int port = Integer.parseInt(args[0]); // Receiving Port

		ServerSocket servSock = new ServerSocket(port);
		// Change Bin to Text on both client and server for different encoding
		VoteMsgCoder coder = new VoteMsgBinCoder();
		VoteService service = new VoteService();

		while (true) {
			Socket clntSock = servSock.accept();
			System.out.println("Handling client at "
					+ clntSock.getRemoteSocketAddress());

			// Change Length to Delim for a different framing strategy
			Framer framer = new LengthFramer(clntSock.getInputStream());
			try {
				byte[] req;
				while ((req = framer.nextMsg()) != null) {
					System.out.println("Received message (" + req.length
							+ " bytes)");
					VoteMsg responseMsg = service.handleRequest(coder
							.fromWire(req));
					framer.frameMsg(coder.toWire(responseMsg),
							clntSock.getOutputStream());
				}
				System.out.println("vote details:");
				Map<Long, Long> details = service.getResults();
				for (Long key : details.keySet()) {
					System.out.println("id: "  + key + ", count: " + details.get(key));
				}
			} catch (IOException ioe) {
				System.err
						.println("Error handling client: " + ioe.getMessage());
			} finally {
				System.out.println("Closing connection");
				clntSock.close();
			}
		}
	}
}

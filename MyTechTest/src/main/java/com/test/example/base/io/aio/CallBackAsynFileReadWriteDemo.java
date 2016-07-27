package com.test.example.base.io.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * 回调的方式异步读取文件内容
 * 
 * @author Administrator
 *
 */
public class CallBackAsynFileReadWriteDemo {

	public static void main(String[] args) throws InterruptedException, IOException {
		Path file = Paths.get("D:/test/test.txt");
		int pos = 0;
		ByteBuffer buffer = ByteBuffer.allocate(1000);
		MyCompletionHandler handler = new MyCompletionHandler(pos, buffer);
		AsynchronousFileChannel channel = AsynchronousFileChannel.open(file);
		channel.read(buffer, 0, channel, handler);

		TimeUnit.SECONDS.sleep(1);
		
		System.out.println(handler.isEnd);
		channel.close();
	}

}

class MyCompletionHandler implements CompletionHandler<Integer, AsynchronousFileChannel> {

	public MyCompletionHandler(int pos, ByteBuffer buffer) {
		this.pos = pos;
		this.buffer = buffer;
	}

	int pos = 0;
	ByteBuffer buffer = null;
	boolean isEnd = false;

	public void completed(Integer result, AsynchronousFileChannel attachment) {
		if (result != -1) {
			System.out.println("...................................");
			pos += result;
			System.out.print(new String(buffer.array(), 0, result));

			buffer.flip();
			attachment.read(buffer, pos, attachment, this);
		} else {
			System.out.println("-------------read ended----------------------");
			isEnd = true;
			return;
		}
	}

	public void failed(Throwable exc, AsynchronousFileChannel attachment) {
		System.err.println("Error!");
		exc.printStackTrace();
	}

	public boolean isEnd() {
		return isEnd;
	}

}

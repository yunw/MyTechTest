package com.test.example.base.io.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 回调的方式异步读取文件内容
 * 
 * @author Administrator
 *
 */
public class CallBackAsynFileReadWriteDemo {

	public static void main(String[] args) throws InterruptedException, IOException {
		Path file = Paths.get("D:/test/test1.txt");
		ExecutorService pool = new ScheduledThreadPoolExecutor(3);
		int pos = 0;
		ByteBuffer buffer = ByteBuffer.allocate(1000);
		MyCompletionHandler handler = new MyCompletionHandler(pos, buffer);
		AsynchronousFileChannel channel = AsynchronousFileChannel.open(file, EnumSet.of(StandardOpenOption.READ), pool);
		channel.read(buffer, 0, channel, handler);
		pool.awaitTermination(5, TimeUnit.SECONDS);
		pool.shutdown();
	}

}

class MyCompletionHandler implements CompletionHandler<Integer, AsynchronousFileChannel> {

	public MyCompletionHandler(int pos, ByteBuffer buffer) {
		this.pos = pos;
		this.buffer = buffer;
	}

	// need to keep track of the next position.
	int pos = 0;
	ByteBuffer buffer = null;

	ExecutorService pool = new ScheduledThreadPoolExecutor(3);

	public void completed(Integer result, AsynchronousFileChannel attachment) {
		if (result != -1) {
			pos += result;
			// 这里必须读取0,result直接的buffer，否则会读取到垃圾数据
			System.out.print(new String(buffer.array(), 0, result));

			buffer.flip();
			attachment.read(buffer, pos, attachment, this);
		} else {
			return;
		}
	}

	public void failed(Throwable exc, AsynchronousFileChannel attachment) {
		System.err.println("Error!");
		exc.printStackTrace();
	}

}

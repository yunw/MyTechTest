package com.test.example.base.io.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 未来式异步读取文件内容
 * 
 * @author Administrator
 *
 */
public class futureAsynFileReadWriteDemo {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		futureDemo("D:/test/test1.txt");
	}

	private static void futureDemo(String path) throws InterruptedException, ExecutionException, IOException {
		Path filePath = Paths.get(path);
		try (AsynchronousFileChannel afc = AsynchronousFileChannel.open(filePath, StandardOpenOption.READ)) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(1 * 1024);
			long offset = 0;
			Future<Integer> result = null;
			while (true) {
				result = afc.read(byteBuffer, offset);
				while (!result.isDone()) {
					System.out.println("Waiting file channel finished....");
					Thread.sleep(1);
				}
				if (result.get() == -1) {
					break;
				}
				System.out.println("Finished? = " + result.isDone());
				System.out.println("byteBuffer = " + result.get());
				byte[] b = new byte[result.get()];
				byteBuffer.flip();
				byteBuffer.get(b);
				System.out.println(new String(b));
				offset += result.get();
				byteBuffer.position(0);
			}
			;
		}
	}

}

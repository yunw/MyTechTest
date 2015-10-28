package com.test.example.base.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
	
	public static void main(String[] args) throws Exception {

	    Path file = Paths.get("D:/test/test1.txt");
	    AsynchronousFileChannel channel = AsynchronousFileChannel.open(file);

	    ByteBuffer buffer = ByteBuffer.allocate(100_000);

	    channel.read(buffer, 0, buffer,
	        new CompletionHandler<Integer, ByteBuffer>() {
	          public void completed(Integer result, ByteBuffer attachment) {
	            System.out.println("Bytes read [" + result + "]");
	          }

	          public void failed(Throwable exception, ByteBuffer attachment) {
	            System.out.println(exception.getMessage());
	          }
	        });
	  }

//	public static void main(String args[]) throws Exception {
//		ExecutorService pool = new ScheduledThreadPoolExecutor(3);
//		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("D:/test/test1.txt"),
//		        EnumSet.of(StandardOpenOption.READ), pool);
//		CompletionHandler<Integer, ByteBuffer> handler = new CompletionHandler<Integer, ByteBuffer>() {
//			@Override
//			public synchronized void completed(Integer result, ByteBuffer attachment) {
//				for (int i = 0; i < attachment.limit(); i++) {
//					System.out.println((char) attachment.get(i));
//				}
//			}
//
//			@Override
//			public void failed(Throwable e, ByteBuffer attachment) {
//			}
//		};
//		final int bufferCount = 5;
//		ByteBuffer buffers[] = new ByteBuffer[bufferCount];
//		for (int i = 0; i < bufferCount; i++) {
//			buffers[i] = ByteBuffer.allocate(10);
//			fileChannel.read(buffers[i], i * 10, buffers[i], handler);
//		}
//		pool.awaitTermination(1, TimeUnit.SECONDS);
//		for (ByteBuffer byteBuffer : buffers) {
//			for (int i = 0; i < byteBuffer.limit(); i++) {
//				System.out.print((char) byteBuffer.get(i));
//			}
//		}
//	}
}

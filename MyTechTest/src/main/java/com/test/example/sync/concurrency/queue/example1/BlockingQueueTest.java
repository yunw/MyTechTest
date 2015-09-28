package com.test.example.sync.concurrency.queue.example1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueTest {

	public static void main(String[] args) {
		// 声明一个容量为10的缓存队列
		BlockingQueue<Product> queue = new LinkedBlockingQueue<Product>(500);

		Producer producer1 = new Producer(queue);
		Producer producer2 = new Producer(queue);
		Producer producer3 = new Producer(queue);
		Consumer consumer = new Consumer(queue);

		// 借助Executors
		ExecutorService service = Executors.newCachedThreadPool();
		// 启动线程
		service.execute(producer1);
		service.execute(producer2);
		service.execute(producer3);
		service.execute(consumer);

		try {
	        TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
		producer1.stop();
		producer2.stop();
		producer3.stop();
		consumer.stop();
		service.shutdown();
	}

}

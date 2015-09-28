package com.test.example.sync.concurrency.queue.example1;

import java.util.concurrent.BlockingQueue;

/**
 * 消费者
 * @author Administrator
 *
 */
public class Consumer implements Runnable {

	private BlockingQueue<Product> queue;
	
	private boolean stop = false;

	public Consumer(BlockingQueue<Product> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (!stop || queue.size() > 0) {
			Product p = queue.poll();
			if (p == null) {
				continue;
			}
			System.out.println(queue.size());
			System.out.println(p.getName());
		}
		System.out.println("stop---------------------");
	}
	
	public void stop() {
		this.stop = true;
	}

}

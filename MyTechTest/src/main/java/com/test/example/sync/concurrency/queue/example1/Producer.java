package com.test.example.sync.concurrency.queue.example1;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者
 * @author Administrator
 *
 */
public class Producer implements Runnable {

	private BlockingQueue<Product> queue;
	
	private boolean stop = false;

	public Producer(BlockingQueue<Product> productQueue) {
		this.queue = productQueue;
	}

	@Override
	public void run() {
		while (!stop) {
			Timestamp t = new Timestamp(Calendar.getInstance().getTimeInMillis());
			Product p = new Product();
			p.setName("p" + t);
			try {
				queue.put(p);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("stop=================");
	}
	
	public void stop() {
		this.stop = true;
	}

}

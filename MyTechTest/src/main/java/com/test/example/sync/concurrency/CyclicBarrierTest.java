package com.test.example.sync.concurrency;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier:一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier
 * point)。在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier
 * 在释放等待线程后可以重用，所以称它为循环 的 barrier。它支持一个可选命令，在所有子线程都到达屏障点后并且子线程结束前执行一次。
 * 
 * 使用场景：需要所有的子任务都达到某个点，然后才能继续执行时，这个时候就可以选择使用CyclicBarrier。
 * 
 * 本例：组团旅游，游客在指定地点汇合（子任务），导游清点人数，都到齐了，然后上车出发去景点（可选），游客兴奋地喊叫（子任务继续）。
 * 
 * @author Administrator
 *
 */
public class CyclicBarrierTest {

	public static final String[] ADDRESSES = new String[] { "西湖", "天目湖", "千岛湖" };

	public static void main(String[] args) throws IOException, InterruptedException {
		CyclicBarrier barrier = new CyclicBarrier(3, new BarrierAction());

		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(new Thread(new Runner(barrier, "游客张三")));
		executor.submit(new Thread(new Runner(barrier, "游客李四")));
		executor.submit(new Thread(new Runner(barrier, "游客王五")));

		executor.shutdown();
	}
	
}

class BarrierAction implements Runnable {
	
	private int index = 0;

	public void run() {
		System.out.println("\n导游：大家好！都到齐了，那我们上车走人，第" + ++index + "站：" + CyclicBarrierTest.ADDRESSES[index - 1]);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("1个小时后，" + CyclicBarrierTest.ADDRESSES[index - 1] + "到了...\n");
	}

}

class Runner implements Runnable {
	// 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)
	private CyclicBarrier barrier;

	private String name;

	public Runner(CyclicBarrier barrier, String name) {
		super();
		this.barrier = barrier;
		this.name = name;
	}

	@Override
	public void run() {
		for (int i = 1; i <= CyclicBarrierTest.ADDRESSES.length; i++) {
			trip(i);
		}
	}

	private void trip(int index) {
		try {
			System.out.println(name + "：导游好!大家好!我到了...");
			// barrier的await方法，在所有参与者都已经在此 barrier上调用 await方法之前，将一直等待。
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println(name + "：哇！" + CyclicBarrierTest.ADDRESSES[index - 1] + "真漂亮...");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

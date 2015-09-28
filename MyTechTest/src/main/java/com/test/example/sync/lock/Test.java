package com.test.example.sync.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

public abstract class Test {
	protected String id;
	protected CyclicBarrier barrier;
	protected long count;
	protected int threadNum;
	protected ExecutorService executor;

	public Test(String id, CyclicBarrier barrier, long count, int threadNum,
			ExecutorService executor) {
		this.id = id;
		this.barrier = barrier;
		this.count = count;
		this.threadNum = threadNum;
		this.executor = executor;
	}

	public void startTest() {
		long start = System.currentTimeMillis();
		for (int j = 0; j < threadNum; j++) {
			executor.execute(new Thread() {
				@Override
				public void run() {
					for (int i = 0; i < count; i++) {
						test();
					}
					try {
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		// 所有线程执行完成之后，才会跑到这一步
		long duration = System.currentTimeMillis() - start;
		System.out.println(id + " = " + duration);
	}

	protected abstract void test();
}

/**
 * public void lock() 获取锁。 如果该锁没有被另一个线程保持，则获取该锁并立即返回，将锁的保持计数设置为 1。
 * 如果当前线程已经保持该锁，则将保持计数加 1，并且该方法立即返回。
 * 如果该锁被另一个线程保持，则出于线程调度的目的，禁用当前线程，并且在获得锁之前，该线程将一直处于休眠状态，此时锁保持计数被设置为 1。
 */

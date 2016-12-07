package com.test.example.jedis.lock;

import java.util.UUID;

public class RedisLockTest {

	public static void main(String[] args) throws InterruptedException {
		noLockTest();
		redisLockTest();
	}

	public static void redisLockTest() throws InterruptedException {
		StringBuffer sb = new StringBuffer(200);
		String key = "print";
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				char[] cs = "abcdefghij".toCharArray();
				boolean getLock = false;
				String value = UUID.randomUUID().toString();
				do {
					getLock = DistributedRedisLock.lock(key, value);
				} while(getLock == true);
				for (int i = 0; i < 10; i++) {
					for (char c : cs) {
						sb.append(String.valueOf(c));
					}
				}
				DistributedRedisLock.unlock(key, value);
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				char[] cs = "0123456789".toCharArray();
				boolean getLock = false;
				String value = UUID.randomUUID().toString();
				do {
					getLock = DistributedRedisLock.lock(key, value);
				} while(getLock == true);
				for (int i = 0; i < 10; i++) {
					for (char c : cs) {
						sb.append(String.valueOf(c));
					}
				}
				DistributedRedisLock.unlock(key, value);
			}
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(sb.toString());
	}

	public static void noLockTest() throws InterruptedException {
		StringBuffer sb = new StringBuffer(200);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				char[] cs = "abcdefghij".toCharArray();
				for (int i = 0; i < 10; i++)
					for (char c : cs) {
						sb.append(String.valueOf(c));
					}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				char[] cs = "0123456789".toCharArray();
				for (int i = 0; i < 10; i++)
					for (char c : cs) {
						sb.append(String.valueOf(c));
					}
			}
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(sb.toString());
	}
}

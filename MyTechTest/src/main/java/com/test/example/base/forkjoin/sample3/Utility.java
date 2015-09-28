package com.test.example.base.forkjoin.sample3;

import java.util.concurrent.TimeUnit;

/**
 * 工具类，放一些简单的方法
 * @author Administrator
 *
 */
public class Utility {

	public static void sleep(int nSecond) {
			try {
				TimeUnit.SECONDS.sleep(nSecond);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public static void log(String sMsg) {
		System.err.println(sMsg);
	}

	public static void log(int sMsg) {
		System.err.println(sMsg);
	}
}

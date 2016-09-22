package com.test.example.base.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 多线程超时终止
 * @author Administrator
 *
 */
public class MultiThreadTimeoutDemo {

	public static void main(String[] args) {
		System.out.println("Start ...");

		ExecutorService exec = Executors.newCachedThreadPool();

		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task("1"));
		taskList.add(new Task("2"));
		taskList.add(new Task("3"));
		taskList.add(new Task("4"));
		taskList.add(new Task("5"));
		try {
			List<Future<String>> boolList = exec.invokeAll(taskList, 5, TimeUnit.SECONDS);//5秒超时
			for (Future<String> future : boolList) {
			    if (future.isDone()) {
			    System.out.println("\n-----g------" + future.get());
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		exec.shutdown();
		System.out.println("\nEnd!");
	}

}

class Task implements Callable<String> {

	private String sequence;

	public Task(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public String call() throws Exception {
		long radom = Math.round(Math.random() * 80);
		System.out.println("thread" + sequence + ": " + radom/10.0 + "秒.");
		for (int i = 0; i < radom; i++) {
			Thread.sleep(100); // 睡眠0.1秒
			System.out.print(sequence);
		}
		System.out.println("\n---thread" + sequence + " normal over.------");
		return sequence;
	}
}

package com.test.example.base.forkjoin.sample2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
public class Fibonacci extends RecursiveTask<Long> {

	final int n;
	
	final int[] results = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597 };

	Fibonacci(int n) {
		this.n = n;
	}

	private long compute(int small) {
		return results[small];
	}

	@Override
	public Long compute() {
		if (n <= results.length) {
			return compute(n - 1);
		}
		Fibonacci f1 = new Fibonacci(n - 1);
		Fibonacci f2 = new Fibonacci(n - 2);
		System.out.println("fork new thread for " + (n - 1));
		f1.fork();
		System.out.println("fork new thread for " + (n - 2));
		f2.fork();
		return f1.join() + f2.join();
	}
	
	public static void main(String[] args) {
		Fibonacci problem = new Fibonacci(414);
		int nThreads = Runtime.getRuntime().availableProcessors();
		ForkJoinPool fjPool = new ForkJoinPool(nThreads);
		fjPool.invoke(problem);
		System.out.printf("Result is:%d%n", problem.getRawResult());
	}
	
}

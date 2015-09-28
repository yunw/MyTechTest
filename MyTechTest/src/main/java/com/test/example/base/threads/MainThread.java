package com.test.example.base.threads;

import java.util.concurrent.LinkedBlockingQueue;

public class MainThread {
	
	public static void main(String[] args) {
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		queue.add("aaa");
		System.out.println(queue.contains("aaa"));
	}
	
	

}

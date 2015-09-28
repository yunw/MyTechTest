package com.test.example.jvm;

public class DeadLoop {

	public static void main(String[] args) {
		long i = 0;
		while(true) {
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + (i++));
		}
	}

}

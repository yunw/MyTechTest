package com.test.example.mianshi;

public class Test extends Test1 {
	
	public static void main(String[] args) {
		int a = 0x0f;//=0 * 16^1 + f * 16^0 = 15; ==> 00001111;
		int b = 0x31;//=3 * 16^1 + 1 * 16^0 = 49; ==> 00110001
		int c = a&b;//                            ==> 00000001; ==> 1;
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		short s1 = 1; s1 += 1;
		char ch = '中';
	}

}

abstract class Test1 {
	public static void main(String[] args) {
		Test1 t = new Test();
		Thread thread = new Thread();
		thread.start();
	}
}

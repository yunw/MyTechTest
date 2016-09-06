package com.test.example.mianshi.arithmetic.mianshi;

public class Two {

	@SuppressWarnings("unused")
    public static void main(String[] args) {
		int x = 4;
		java.util.Date date = (x > 4) ? new A() : new B();
		System.out.println(5.0942*1000);
	     System.out.println(5.0943*1000);
	     System.out.println(5.0944*1000);
	}
}

@SuppressWarnings("serial")
class A extends java.util.Date {
}

@SuppressWarnings("serial")
class B extends java.util.Date {
}

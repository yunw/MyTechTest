package com.test.example.mianshi.outer;

public class Outer {

	private String a;
	
	private static String b;

	// 定义在方法外部的内部类
	@SuppressWarnings("unused")
    private class InnerOutMethod {
		// 非静态内部类中不能定义静态变量。
		// private static String a;
		private String s;

		public InnerOutMethod() {
			// 内部类可以直接访问外部类的成员变量
			s = a;
		}
	}
	
	public static void gett() {
		@SuppressWarnings("unused")
        class InnerInStaticMethod {
			
		}
	}

	@SuppressWarnings("unused")
    public void get() {
		// 定义在方法内部的内部类：不能有访问修饰符，可以由final、abstract修饰
		// 必须先定义后实现。
		String gg = null;
		final String fs = null;
		class InnerInMethod {
			private String ss;

			public InnerInMethod() {
				// 不能访问方法的非final变量
//				 ss = gg;
				ss = fs;
			}
			
		}
		new Thread(new Runnable() {
			public void run() {
				System.out.println("aa");
			};
		}).start();
	}
	
	//静态内部类
	static class NetedClass {
		@SuppressWarnings("unused")
        private String n;
		public String get() {
//			n = s;
			n = b;
			return null;
		}
		
	}

	public static void main(String[] args) {
		Outer outer = new Outer();
		outer.get();
		outer.getClass();
	}

}

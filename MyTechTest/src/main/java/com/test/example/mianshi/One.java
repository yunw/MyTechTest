package com.test.example.mianshi;

public class One {
    
    private long b = 0;
    
    public void set0() {
        b = 0;
    }
    
    public void set1() {
        b = 1;
    }
    
    public void check() {
        if (0 != b && 1 != b) {
            System.err.println("Error");
        }
    }
    
    public static void main(final String[] args) {
        final One v = new One();
     
        // 线程 1：设置 b = 0
        final Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    v.set0();
                }
            };
        };
        t1.start();
     
        // 线程 2：设置 b = 1
        final Thread t2 = new Thread() {
            public void run() {
                while (true) {
                    v.set1();
                }
            };
        };
        t2.start();
     
        // 线程 3：检查 0 != b && -1 != b
        final Thread t3 = new Thread() {
            public void run() {
                while (true) {
                    v.check();
                }
            };
        };
        t3.start();
    }

}

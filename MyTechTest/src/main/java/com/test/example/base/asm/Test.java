package com.test.example.base.asm;

public class Test {
    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Generator.gen();
        C c = new C();
        c.m();
        Class<? extends C> cc = c.getClass();
        System.out.println(cc.getField("timer").get(c));
    }
}

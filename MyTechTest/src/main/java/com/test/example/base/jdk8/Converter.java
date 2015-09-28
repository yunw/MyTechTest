package com.test.example.base.jdk8;

//@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}

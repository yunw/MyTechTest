package com.test.example.base.design23.observer;

/**
 * 观察者
 * @author yinshunlin
 *
 */
public class ConcreteWatcher implements Watcher {

    @Override
    public void update(String str) {
        System.out.println(str);
    }

}

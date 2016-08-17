package com.test.example.base.design23.observer;

/**
 * 被观察者
 * @author yinshunlin
 *
 */
public interface Watched {

    public void addWatcher(Watcher watcher);

    public void removeWatcher(Watcher watcher);

    public void notifyWatchers(String str);
    
}

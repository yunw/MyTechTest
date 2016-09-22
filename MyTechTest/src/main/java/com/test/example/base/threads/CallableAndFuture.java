package com.test.example.base.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多任务并行执行，按执行完毕的先后顺序获得执行结果
 * @author yinshunlin
 *
 */
public class CallableAndFuture {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
        int start = 0;
        int end = 5;
        for(int i = start; i < end; i++) {
            final int taskID = i;
            cs.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    return taskID;
                }
            });
        }
        
        // 可能做一些事情
        for(int i = start; i < end; i++) {
            try {
                System.out.println(cs.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
} 
package com.atguigu.springcloud.current.completableFuture;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Name AsyncTaskTest
 * @Description
 * @Author qfu1
 * @Date 2022-08-30
 */
public class AsyncTaskTest {
    public static void main(String[] args) {
        Executor executor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(500),
                new ThreadPoolExecutor.CallerRunsPolicy());

        AsyncTask<Integer, Integer> asyncTask = new AsyncTask();
        ArrayList<Integer> integers1 = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> integers = asyncTask.sendAsyncBatch(integers1, executor, new TaskLoader<Integer, Integer>() {
            @Override
            public Integer load(Integer integer) throws InterruptedException {
                return 10 * integer;
            }
        });

        System.out.println(integers);
    }
}

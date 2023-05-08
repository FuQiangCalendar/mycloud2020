package com.atguigu.springcloud.current.completableFuture;

import cn.hutool.core.collection.CollUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/**
 * @Name AsyncTask
 * @Description
 * @Author qfu1
 * @Date 2022-08-30
 */
public class AsyncTask<R, P> {

    public List<R> sendAsyncBatch(List<P> list, Executor executor, TaskLoader<R,P> loader) {

        List<R> resultList = new CopyOnWriteArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            Executor finalExecutor = executor;
            // 将任务拆分分成每50个为一个任务
            CollUtil.split(list, 50)
                    .forEach(tempList -> {
                        CompletableFuture[] completableFutures = tempList.stream()
                                .map(p -> CompletableFuture.supplyAsync(() -> {
                                                    try {
                                                        return loader.load(p);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                    return null;
                                                }, finalExecutor)
                                                .handle((result, throwable) -> {
                                                    if (Objects.nonNull(throwable)) {
                                                        //log.error("async error:{}", throwable.getMessage());
                                                    } else if (Objects.nonNull(result)) {
                                                        //log.info("async success:{}", result);
                                                    } else {
                                                        //log.error("async result is null");
                                                    }
                                                    return result;
                                                }).whenComplete((r, ex) -> {
                                                    if (Objects.nonNull(r)) {
                                                        resultList.add((R) r);
                                                    }
                                                })
                                ).toArray(CompletableFuture[]::new);
                        CompletableFuture.allOf(completableFutures).join();
                        System.out.println(resultList.size());
                    });
        }
        return resultList;
    }
}

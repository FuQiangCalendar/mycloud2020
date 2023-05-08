package com.atguigu.springcloud.current.completableFuture;

@FunctionalInterface
public interface TaskLoader<T,P> {

    T load(P p) throws InterruptedException;
}
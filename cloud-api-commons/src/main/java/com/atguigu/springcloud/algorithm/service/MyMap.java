package com.atguigu.springcloud.algorithm.service;

/**
 * @author FuQiangCalendar
 * @title: MyMap
 * @projectName cloud2020
 * @description: TODO
 * @date 2021/10/11 8:50
 */
public interface MyMap<K, V> {
    // 插入一个值
    V put(K key, V value);

    // 获取一个值
    V get(K key);

    // HashMap 中的节点
    interface Entry<K, V>{
        K getKey();

        V getValue();
    }

}

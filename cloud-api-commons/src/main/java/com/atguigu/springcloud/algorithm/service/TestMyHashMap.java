package com.atguigu.springcloud.algorithm.service;

import com.atguigu.springcloud.algorithm.service.impl.MyHashMap;

public class TestMyHashMap {
 
    public static void main(String[] args) {
 
        MyMap<String, Object> map = new MyHashMap<>();
        map.put("name", "zuozhen");
        map.put("age", 23);
        map.put("weight", 75);
        map.put(null, "zuozhen2");
 
        System.out.println(map.get("name"));
        System.out.println(map.get("age"));
        System.out.println(map.get("weight"));
        System.out.println(map.get(null));
 
        map.put("name", "zuozhen1");
        System.out.println(map.get("name"));

        int str = 123;
        int encodeStr = 4856;
        // 异或 加密
        System.out.println(str ^ encodeStr);
        // 双异或 解密
        System.out.println(str ^ encodeStr ^ encodeStr);
    }
}
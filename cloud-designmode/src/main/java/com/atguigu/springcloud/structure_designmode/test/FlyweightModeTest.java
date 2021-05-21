package com.atguigu.springcloud.structure_designmode.test;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.atguigu.springcloud.structure_designmode.test
 * @ClassName FlyweightModeTest
 * @Description 结构模型 -- 享元
 *
 * 运用共享技术有效地支持大量细粒度的对象。
 * 享元（Flyweight）的核心思想很简单：如果一个对象实例一经创建就不可变，那么反复创建相同的实例就没有必要，直接向调用方返回一个共享的实例就行，这样即节省内存，又可以减少创建对象的过程，提高运行速度。
 *
 * 享元模式在Java标准库中有很多应用。我们知道，包装类型如Byte、Integer都是不变类，因此，反复创建同一个值相同的包装类型是没有必要的。
 * 以Integer为例，如果我们通过Integer.valueOf()这个静态工厂方法创建Integer实例，当传入的int范围在-128~+127之间时，会直接返回缓存的Integer实例：
 *
 * 在实际应用中，我们经常使用成熟的缓存库，例如Guava的Cache，因为它提供了最大缓存数量限制、定时过期等实用功能。
 *
 * 小结：
 * 享元模式的设计思想是尽量复用已创建的对象，常用于工厂方法内部的优化。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/21 13:44
 * @Version 1.0
 **/
@Slf4j
public class FlyweightModeTest {

    public static void main(String[] args) {
        test1();
        test2();
    }

    /**
    * 享元模式
     * 对于Byte来说，因为它一共只有256个状态，所以，通过Byte.valueOf()创建的Byte实例，全部都是缓存对象。
     * 因此，享元模式就是通过工厂方法创建对象，在工厂方法内部，很可能返回缓存的实例，而不是新创建实例，从而实现不可变实例的复用。
     *  总是使用工厂方法而不是new操作符创建实例，可获得享元模式的好处。
    */
    public static void test1 () {
        Integer n1 = Integer.valueOf(100);
        Integer n2 = Integer.valueOf(100);
        System.out.println(n1 == n2); // true
    }
    
    public static void test2 () {
        Student st1 = Student.create(1, "啦啦啦啦");
        Student st2 = Student.create(1, "啦啦啦啦");
        System.out.println(st1.equals(st2));
    }
}

/**
* @Description :在实际应用中，享元模式主要应用于缓存，即客户端如果重复请求某些对象，不必每次查询数据库或者读取文件，
 * 而是直接返回内存中缓存的数据。
 * 我们以Student为例，设计一个静态工厂方法，它在内部可以返回缓存的对象：
* @Author:FuQiangCalendar
* @Date: 2021/5/21 15:54
*/
class Student {
    // 持有缓存:
    private static final Map<String, Student> cache = new HashMap<>();

    // 静态工厂方法:
    public static Student create(int id, String name) {
        String key = id + "\n" + name;
        // 先查找缓存:
        Student std = cache.get(key);
        if (std == null) {
            // 未找到,创建新对象:
            System.out.println(String.format("create new Student(%s, %s)", id, name));
            std = new Student(id, name);
            // 放入缓存:
            cache.put(key, std);
        } else {
            // 缓存中存在:
            System.out.println(String.format("return cached Student(%s, %s)", std.id, std.name));
        }
        return std;
    }

    private final int id;
    private final String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
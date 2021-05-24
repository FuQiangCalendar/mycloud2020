package com.atguigu.springcloud.behavior_designmode.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Package com.atguigu.springcloud.behavior_designmode.test
 * @ClassName IteratorModeTest
 * @Description 行为型模式 -- 迭代器
 *
 * 提供一种方法顺序访问一个聚合对象中的各个元素，而又不需要暴露该对象的内部表示。
 *
 * 迭代器模式（Iterator）实际上在Java的集合类中已经广泛使用了。我们以List为例，要遍历ArrayList，即使我们知道它的内部存储了一个Object[]数组，也不应该直接使用数组索引去遍历，因为这样需要了解集合内部的存储结构。如果使用Iterator遍历，那么，ArrayList和LinkedList都可以以一种统一的接口来遍历：
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/21 17:27
 * @Version 1.0
 **/
@Slf4j
public class IteratorModeTest {

    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test1 () {
        List<String> list = new ArrayList<>(Arrays.asList("1","2", "3", "a", "c", "b"));
        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
            String s = it.next();
            log.info(s);
        }
    }

    public static void test2 () {
        ReverseArrayCollection<String> reverseArrayCollection = new ReverseArrayCollection("1", "2", "3", "a", "c", "b");
        Iterator<String> iterator = reverseArrayCollection.iterator();

        while (iterator.hasNext()) {
            log.info(iterator.next());
        }
    }
}

/**
* 虽然我们对如何使用Iterator有了一定了解，但如何实现一个Iterator模式呢？
 * 我们以一个自定义的集合为例，通过Iterator模式实现倒序遍历
 *
 * 实现Iterator模式的关键是返回一个Iterator对象，该对象知道集合的内部结构，因为它可以实现倒序遍历。我们使用Java的内部类实现这个Iterator
 *
 * 使用内部类的好处是内部类隐含地持有一个它所在对象的this引用，可以通过ReverseArrayCollection.this引用到它所在的集合。
 * 上述代码实现的逻辑非常简单，但是实际应用时，如果考虑到多线程访问，当一个线程正在迭代某个集合，
 * 而另一个线程修改了集合的内容时，是否能继续安全地迭代，还是抛出ConcurrentModificationException，就需要更仔细地设计。
 *
 * 小结：
 * Iterator模式常用于遍历集合，它允许集合提供一个统一的Iterator接口来遍历元素，同时保证调用者对集合内部的数据结构一无所知，从而使得调用者总是以相同的接口遍历各种不同类型的集合
* @Author:FuQiangCalendar
* @Date: 2021/5/24 15:27
*/
class ReverseArrayCollection<T> implements Iterable<T> {
    // 以数组形式持有集合:
    private T[] array;

    public ReverseArrayCollection(T... objs) {
        this.array = Arrays.copyOfRange(objs, 0, objs.length);
    }

    public Iterator<T> iterator() {
        return new ReverseIterator();
    }

    class ReverseIterator implements Iterator<T> {
        // 索引位置:
        int index;

        public ReverseIterator() {
            // 创建Iterator时,索引在数组末尾:
            this.index = ReverseArrayCollection.this.array.length;
        }

        public boolean hasNext() {
            // 如果索引大于0,那么可以移动到下一个元素(倒序往前移动):
            return index > 0;
        }

        public T next() {
            // 将索引移动到下一个元素并返回(倒序往前移动):
            index--;
            return array[index];
        }
    }
}



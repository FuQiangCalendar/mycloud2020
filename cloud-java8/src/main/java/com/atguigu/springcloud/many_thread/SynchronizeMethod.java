package com.atguigu.springcloud.many_thread;

/**
 * @Package com.atguigu.springcloud.many_thread
 * @ClassName SynchronizeMethod
 * @Description 同步方法
 *
 * 我们知道Java程序依靠synchronized对线程进行同步，使用synchronized的时候，锁住的是哪个对象非常重要。
 * 让线程自己选择锁对象往往会使得代码逻辑混乱，也不利于封装。更好的方法是把synchronized逻辑封装起来。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/19 13:08
 * @Version 1.0
 **/
public class SynchronizeMethod {
    public static void main(String[] args) {

    }

    /**
    * 线程调用add()、dec()方法时，它不必关心同步逻辑，因为synchronized代码块在add()、dec()方法内部。
     * 并且，我们注意到，synchronized锁住的对象是this，即当前实例，这又使得创建多个Counter实例的时候，它们之间互不影响，可以并发执行：
     *
     * 现在，对于Counter类，多线程可以正确调用。
     * 如果一个类被设计为允许多线程正确访问，我们就说这个类就是“线程安全”的（thread-safe），上面的Counter类就是线程安全的。Java标准库的java.lang.StringBuffer也是线程安全的。
     * 还有一些不变类，例如String，Integer，LocalDate，它们的所有成员变量都是final，多线程同时访问时只能读不能写，这些不变类也是线程安全的。
     * 最后，类似Math这些只提供静态方法，没有成员变量的类，也是线程安全的。
     * 除了上述几种少数情况，大部分类，例如ArrayList，都是非线程安全的类，我们不能在多线程中修改它们。但是，如果所有线程都只读取，不写入，那么ArrayList是可以安全地在线程间共享的。
     *
     * 我们再观察Counter的代码：
     *
     * public class Counter {
     *     public void add(int n) {
     *         synchronized(this) {
     *             count += n;
     *         }
     *     }
     *     ...
     * }
     * 当我们锁住的是this实例时，实际上可以用synchronized修饰这个方法。下面两种写法是等价的：
     *
     * public void add(int n) {
     *     synchronized(this) { // 锁住this
     *         count += n;
     *     } // 解锁
     * }
     * public synchronized void add(int n) { // 锁住this
     *     count += n;
     * } // 解锁
     * 因此，用synchronized修饰的方法就是同步方法，它表示整个方法都必须用this实例加锁。
     *
     *
     *
     * 我们再思考一下，如果对一个静态方法添加synchronized修饰符，它锁住的是哪个对象？
     * public synchronized static void test(int n) {
     *     ...
     * }
     * 对于static方法，是没有this实例的，因为static方法是针对类而不是实例。但是我们注意到任何一个类都有一个由JVM自动创建的Class实例，因此，对static方法添加synchronized，锁住的是该类的Class实例。上述synchronized static方法实际上相当于：
     * public class Counter {
     *     public static void test(int n) {
     *         synchronized(Counter.class) {
     *             ...
     *         }
     *     }
     * }
     *
     * 我们再考察Counter的get()方法：
     * public class Counter {
     *     private int count;
     *
     *     public int get() {
     *         return count;
     *     }
     *     ...
     * }
     * 它没有同步，因为读一个int变量不需要同步。
     * 然而，如果我们把代码稍微改一下，返回一个包含两个int的对象：
     *
     * public class Counter {
     *     private int first;
     *     private int last;
     *
     *     public Pair get() {
     *         Pair p = new Pair();
     *         p.first = first;
     *         p.last = last;
     *         return p;
     *     }
     *     ...
     * }
     * 就必须要同步了。
     *
     * 小结
     * 用synchronized修饰方法可以把整个方法变为同步代码块，synchronized方法加锁对象是this；
     * 通过合理的设计和数据封装可以让一个类变为“线程安全”；
     * 一个类没有特殊说明，默认不是thread-safe；
     * 多线程能否安全访问某个非线程安全的实例，需要具体问题具体分析。
    */
    public static void test1 () {
        Counter0 c1 = new Counter0();
        Counter0 c2 = new Counter0();

        // 对c1进行操作的线程:
        new Thread(() -> {
            c1.add(1);
        }).start();
        new Thread(() -> {
            c1.dec(1);
        }).start();

        // 对c2进行操作的线程:
        new Thread(() -> {
            c2.add(1);
        }).start();
        new Thread(() -> {
            c2.dec(1);
        }).start();
    }
}


class Counter0 {
    private int count = 0;

    public void add(int n) {
        synchronized(this) {
            count += n;
        }
    }

    public void dec(int n) {
        synchronized(this) {
            count -= n;
        }
    }

    public int get() {
        return count;
    }
}
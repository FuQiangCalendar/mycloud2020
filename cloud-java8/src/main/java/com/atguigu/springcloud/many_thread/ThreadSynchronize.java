package com.atguigu.springcloud.many_thread;

/**
 * @Package com.atguigu.springcloud.many_thread
 * @ClassName ThreadSynchronize
 * @Description 线程同步
 *
 * 当多个线程同时运行时，线程的调度由操作系统决定，程序本身无法决定。因此，任何一个线程都有可能在任何指令处被操作系统暂停，然后在某个时间段后继续执行。
 * 这个时候，有个单线程模型下不存在的问题就来了：如果多个线程同时读写共享变量，会出现数据不一致的问题。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/19 11:50
 * @Version 1.0
 **/
public class ThreadSynchronize {

    public static void main(String[] args) throws Exception {

        test1();
        test2();
    }

    /**
    * 代码很简单，两个线程同时对一个int变量进行操作，一个加10000次，一个减10000次，最后结果应该是0，但是，每次运行，结果实际上都是不一样的。
     * 这是因为对变量进行读取和写入时，结果要正确，必须保证是原子操作。原子操作是指不能被中断的一个或一系列操作。
     *
     * 例如，对于语句：
     * n = n + 1;
     *
     * 看上去是一行语句，实际上对应了3条指令：
     * ILOAD
     * IADD
     * ISTORE
     *
     * 我们假设n的值是100，如果两个线程同时执行n = n + 1，得到的结果很可能不是102，而是101，原因在于：
     * 如果线程1在执行ILOAD后被操作系统中断，此刻如果线程2被调度执行，它执行ILOAD后获取的值仍然是100，最终结果被两个线程的ISTORE写入后变成了101，而不是期待的102。
     * 这说明多线程模型下，要保证逻辑正确，对共享变量进行读写时，必须保证一组指令以原子方式执行：即某一个线程执行时，其他线程必须等待：
     *
     * 通过加锁和解锁的操作，就能保证3条指令总是在一个线程执行期间，不会有其他线程会进入此指令区间。即使在执行期线程被操作系统中断执行，
     * 其他线程也会因为无法获得锁导致无法进入此指令区间。只有执行线程将锁释放后，其他线程才有机会获得锁并执行。
     * 这种加锁和解锁之间的代码块我们称之为临界区（Critical Section），任何时候临界区最多只有一个线程能执行。
     *
     * 可见，保证一段代码的原子性就是通过加锁和解锁实现的。Java程序使用synchronized关键字对一个对象进行加锁：
     * synchronized(lock) {
     *     n = n + 1;
     * }
     * synchronized保证了代码块在任意时刻最多只有一个线程能执行。我们把上面的代码用synchronized改写见test2()：
    */
    public static void test1() throws Exception {
        Thread add = new AddThread();
        Thread dec = new DecThread();
        add.start();
        dec.start();
        add.join();
        dec.join();
        System.out.println(Counter.count);
    }

    /**
    * 注意到代码：
     * synchronized(Counter.lock) { // 获取锁
     *     ...
     * } // 释放锁
     *
     * 它表示用Counter.lock实例作为锁，两个线程在执行各自的synchronized(Counter.lock) { ... }代码块时，必须先获得锁，才能进入代码块进行。
     * 执行结束后，在synchronized语句块结束会自动释放锁。这样一来，对Counter.count变量进行读写就不可能同时进行。上述代码无论运行多少次，
     * 最终结果都是0。
     *
     * 我们来概括一下如何使用synchronized：
     *
     * 找出修改共享变量的线程代码块；
     * 选择一个共享实例作为锁；
     * 使用synchronized(lockObject) { ... }。
     * 在使用synchronized的时候，不必担心抛出异常。因为无论是否有异常，都会在synchronized结束处正确释放锁：
    */
    public static void test2() throws Exception {
        Thread add = new AddThread2();
        Thread dec = new DecThread2();
        add.start();
        dec.start();
        add.join();
        dec.join();
        System.out.println(Counter2.count);
    }
}

class Counter {
    public static int count = 0;
}

class AddThread extends Thread {
    public void run() {
        for (int i=0; i<10000; i++) { Counter.count += 1; }
    }
}

class DecThread extends Thread {
    public void run() {
        for (int i=0; i<10000; i++) { Counter.count -= 1; }
    }
}

class Counter2 {
    public static final Object lock = new Object();
    public static int count = 0;
}

class AddThread2 extends Thread {
    public void run() {
        for (int i=0; i<10000; i++) {
            synchronized(Counter2.lock) {
                Counter.count += 1;
            }
        }
    }
}

class DecThread2 extends Thread {
    public void run() {
        for (int i=0; i<10000; i++) {
            synchronized(Counter2.lock) {
                Counter.count -= 1;
            }
        }
    }
}
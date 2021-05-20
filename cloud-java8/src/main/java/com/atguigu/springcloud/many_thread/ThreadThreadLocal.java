package com.atguigu.springcloud.many_thread;

import lombok.var;

/**
 * @Package com.atguigu.springcloud.many_thread
 * @ClassName ThreadThreadLocal
 * @Description 使用ThreadLocal
 *
 * 多线程是Java实现多任务的基础，Thread对象代表一个线程，我们可以在代码中调用Thread.currentThread()获取当前线程
 *
 * gitee 地址：https://gitee.com/liaoxuefeng/learn-java/blob/master/practices/Java%E6%95%99%E7%A8%8B/130.%E5%A4%9A%E7%BA%BF%E7%A8%8B.1255943750561472/200.%E4%BD%BF%E7%94%A8ThreadLocal.1306581251653666/thread-threadlocal.zip?utm_source=blog_lxf
 *
 * 小结：
 * ThreadLocal表示线程的“局部变量”，它确保每个线程的ThreadLocal变量都是各自独立的；
 * ThreadLocal适合在一个线程的处理流程中保持上下文（避免了同一参数在所有方法中传递）；
 * 使用ThreadLocal要用try ... finally结构，并在finally中清除。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/20 12:57
 * @Version 1.0
 **/
public class ThreadThreadLocal {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1 () {
        log("start test1...");
        new Thread(() -> {
            log("run task...");
        }).start();
        new Thread(() -> {
            log("print...");
        }).start();
        log("end test1.");
    }

    /**
    * 对于多任务，Java标准库提供的线程池可以方便地执行这些任务，同时复用线程。Web应用程序就是典型的多任务应用，每个用户请求页面时，
     * 我们都会创建一个任务，类似：
     * public void process(User user) {
     *     checkPermission();
     *     doWork();
     *     saveStatus();
     *     sendResponse();
     * }
     * 然后，通过线程池去执行这些任务。
     * 观察process()方法，它内部需要调用若干其他方法，同时，我们遇到一个问题：如何在一个线程内传递状态？
     * process()方法需要传递的状态就是User实例。有的童鞋会想，简单地传入User就可以了：
     *
     * public void process(User user) {
     *     checkPermission(user);
     *     doWork(user);
     *     saveStatus(user);
     *     sendResponse(user);
     * }
     * 但是往往一个方法又会调用其他很多方法，这样会导致User传递到所有地方：
     *
     * void doWork(User user) {
     *     queryStatus(user);
     *     checkStatus();
     *     setNewStatus(user);
     *     log();
     * }
     * 这种在一个线程中，横跨若干方法调用，需要传递的对象，我们通常称之为上下文（Context），它是一种状态，可以是用户身份、任务信息等。
     * 给每个方法增加一个context参数非常麻烦，而且有些时候，如果调用链有无法修改源码的第三方库，User对象就传不进去了。
     * Java标准库提供了一个特殊的ThreadLocal，它可以在一个线程中传递同一个对象。
     * ThreadLocal实例通常总是以静态字段初始化如下
    */
    public static void test2 () {
        try (var ctx = new UserContext("Bob")) {
            // 可任意调用UserContext.currentUser():
            String currentUser = UserContext.currentUser();
            log(currentUser);
        } // 在此自动调用UserContext.close()方法释放ThreadLocal关联对象
    }


    static void log(String s) {
        System.out.println(Thread.currentThread().getName() + ": " + s);
    }
}

class UserContext implements AutoCloseable {

    static final ThreadLocal<String> ctx = new ThreadLocal<>();

    public UserContext(String user) {
        ctx.set(user);
    }

    public static String currentUser() {
        System.out.println(Thread.currentThread().getName() + ": " + ctx.get());
        return ctx.get();
    }

    @Override
    public void close() {
        ctx.remove();
    }
}

package com.atguigu.springcloud.many_thread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Package com.atguigu.springcloud.many_thread
 * @ClassName MyThreadPoll
 * @Description 使用线程池
 *
 * Java语言虽然内置了多线程支持，启动一个新线程非常方便，但是，创建线程需要操作系统资源（线程资源，栈空间等），频繁创建和销毁大量线程需要消耗大量时间。
 *
 * 如果可以复用一组线程：
 *
 * ┌─────┐ execute  ┌──────────────────┐
 * │Task1│─────────>│ThreadPool        │
 * ├─────┤          │┌───────┐┌───────┐│
 * │Task2│          ││Thread1││Thread2││
 * ├─────┤          │└───────┘└───────┘│
 * │Task3│          │┌───────┐┌───────┐│
 * ├─────┤          ││Thread3││Thread4││
 * │Task4│          │└───────┘└───────┘│
 * ├─────┤          └──────────────────┘
 * │Task5│
 * ├─────┤
 * │Task6│
 * └─────┘
 *   ...
 * 那么我们就可以把很多小任务让一组线程来执行，而不是一个任务对应一个新线程。这种能接收大量小任务并进行分发处理的就是线程池。
 * 简单地说，线程池内部维护了若干个线程，没有任务的时候，这些线程都处于等待状态。如果有新任务，就分配一个空闲线程执行。如果所有线程都处于忙碌状态，新任务要么放入队列等待，要么增加一个新线程进行处理。
 *
 * Java标准库提供了ExecutorService接口表示线程池
 *
 * 因为ExecutorService只是接口，Java标准库提供的几个常用实现类有：
 * FixedThreadPool：线程数固定的线程池；
 * CachedThreadPool：线程数根据任务动态调整的线程池；
 * SingleThreadExecutor：仅单线程执行的线程池。
 *
 * 小结：
 * JDK提供了ExecutorService实现了线程池功能：
 * 线程池内部维护一组线程，可以高效执行大量小任务；
 * Executors提供了静态方法创建不同类型的ExecutorService；
 * 必须调用shutdown()关闭ExecutorService；
 * ScheduledThreadPool可以定期调度多个任务。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/19 14:51
 * @Version 1.0
 **/
public class MyThreadPool {
    // 创建固定大小的线程池:
//    private static ExecutorService fixedExecutorPool = Executors.newFixedThreadPool(3);
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    /**
    * 我们观察执行结果，一次性放入6个任务，由于线程池只有固定的4个线程，因此，前4个任务会同时执行，等到有线程空闲后，
     * 才会执行后面的两个任务。
     * 线程池在程序结束的时候要关闭。使用shutdown()方法关闭线程池的时候，它会等待正在执行的任务先完成，然后再关闭。
     * shutdownNow()会立刻停止正在执行的任务，awaitTermination()则会等待指定的时间让线程池关闭。
    */
    public static void test1 () {
        // 创建一个固定大小的线程池:
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 6; i++) {
            es.submit(new MyTask("" + i));
        }
        // 关闭线程池:
        es.shutdown();
    }

    /**
    * @Description : CachedThreadPool 线程池执行多个任务
     *
     * 如果我们把线程池改为CachedThreadPool，由于这个线程池的实现会根据任务数量动态调整线程池的大小，所以6个任务可一次性全部同时执行。
     * 如果我们想把线程池的大小限制在4～10个之间动态调整怎么办？我们查看Executors.newCachedThreadPool()方法的源码：
     *
     * public static ExecutorService newCachedThreadPool() {
     *     return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
     *                                     60L, TimeUnit.SECONDS,
     *                                     new SynchronousQueue<Runnable>());
     * }
    */
    public static void test2 () {
//        ExecutorService executorService = Executors.newCachedThreadPool();
        int min = 4;
        int max = 10;
        ExecutorService executorService = new ThreadPoolExecutor(min, max,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        for (int i = 0; i < 6; i++) {
            executorService.submit(new MyTask("" + i));
        }
        // 关闭线程池:
        executorService.shutdown();
    }

    /**
    * ScheduledThreadPool
     * 还有一种任务，需要定期反复执行，例如，每秒刷新证券价格。这种任务本身固定，需要反复执行的，可以使用ScheduledThreadPool。放入ScheduledThreadPool的任务可以定期反复执行。
     * 创建一个ScheduledThreadPool仍然是通过Executors类：
     * ScheduledExecutorService ses = Executors.newScheduledThreadPool(4);
     * 我们可以提交一次性任务，它会在指定延迟后只执行一次：
     *
     * // 1秒后执行一次性任务:
     * ses.schedule(new Task("one-time"), 1, TimeUnit.SECONDS);
     * 如果任务以固定的每3秒执行，我们可以这样写：
     *
     * // 2秒后开始执行定时任务，每3秒执行:
     * ses.scheduleAtFixedRate(new Task("fixed-rate"), 2, 3, TimeUnit.SECONDS);
     * 如果任务以固定的3秒为间隔执行，我们可以这样写：
     *
     * // 2秒后开始执行定时任务，以3秒为间隔执行:
     * ses.scheduleWithFixedDelay(new Task("fixed-delay"), 2, 3, TimeUnit.SECONDS);
     * 注意FixedRate和FixedDelay的区别。FixedRate是指任务总是以固定时间间隔触发，不管任务执行多长时间：
     *
     * │░░░░   │░░░░░░ │░░░    │░░░░░  │░░░
     * ├───────┼───────┼───────┼───────┼────>
     * │<─────>│<─────>│<─────>│<─────>│
     * 而FixedDelay是指，上一次任务执行完毕后，等待固定的时间间隔，再执行下一次任务：
     *
     * │░░░│       │░░░░░│       │░░│       │░
     * └───┼───────┼─────┼───────┼──┼───────┼──>
     *     │<─────>│     │<─────>│  │<─────>│
     * 因此，使用ScheduledThreadPool时，我们要根据需要选择执行一次、FixedRate执行还是FixedDelay执行。
     * 细心的童鞋还可以思考下面的问题：
     * 在FixedRate模式下，假设每秒触发，如果某次任务执行时间超过1秒，后续任务会不会并发执行？
     * 如果任务抛出了异常，后续任务是否继续执行？
     * Java标准库还提供了一个java.util.Timer类，这个类也可以定期执行任务，但是，一个Timer会对应一个Thread，
     * 所以，一个Timer只能定期执行一个任务，多个定时任务必须启动多个Timer，而一个ScheduledThreadPool就可以调度多个定时任务，
     * 所以，我们完全可以用ScheduledThreadPool取代旧的Timer。
    */
    public static void test3 () {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(4);
        for (int i = 0; i < 6; i++) {
//            ses.submit(new MyTask("" + i));
//            ses.schedule(new MyTask("one-time" + i), 1, TimeUnit.SECONDS);
//            ses.scheduleAtFixedRate(new MyTask("fixed-rate" + i), 2, 3, TimeUnit.SECONDS);
            ses.scheduleWithFixedDelay(new MyTask("fixed-delay" + i), 2, 3, TimeUnit.SECONDS);
        }
        // 关闭线程池:
//        ses.shutdown();
    }
}

@Slf4j
@Data
class MyTask implements Runnable {
    private final String name;

    public MyTask (String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("start task " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("end task " + name);
    }
}
package com.atguigu.springcloud.many_thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Package com.atguigu.springcloud.many_thread
 * @ClassName ThreadCondition
 * @Description 使用Condition
 *
 * 使用ReentrantLock比直接使用synchronized更安全，可以替代synchronized进行线程同步。
 * 但是，synchronized可以配合wait和notify实现线程在条件不满足时等待，条件满足时唤醒，用ReentrantLock我们怎么编写wait和notify的功能呢？
 * 答案是使用Condition对象来实现wait和notify的功能。
 * 我们仍然以TaskQueue为例，把前面用synchronized实现的功能通过ReentrantLock和Condition来实现：
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/19 14:00
 * @Version 1.0
 **/
public class ThreadCondition {

}

/**
* 可见，使用Condition时，引用的Condition对象必须从Lock实例的newCondition()返回，这样才能获得一个绑定了Lock实例的Condition实例。
 * Condition提供的await()、signal()、signalAll()原理和synchronized锁对象的wait()、notify()、notifyAll()是一致的，并且其行为也是一样的：
 * await()会释放当前锁，进入等待状态；
 * signal()会唤醒某个等待线程；
 * signalAll()会唤醒所有等待线程；
 * 唤醒线程从await()返回后需要重新获得锁。
 *
 * 此外，和tryLock()类似，await()可以在等待指定时间后，如果还没有被其他线程通过signal()或signalAll()唤醒，可以自己醒来：
 * if (condition.await(1, TimeUnit.SECOND)) {
 *     // 被其他线程唤醒
 * } else {
 *     // 指定时间内没有被其他线程唤醒
 * }
 * 可见，使用Condition配合Lock，我们可以实现更灵活的线程同步。
 *
 * 小结
 * Condition可以替代wait和notify；
 * Condition对象必须从Lock对象获取。
*/
class TaskQueueCondition {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public void addTask(String s) {
        lock.lock();
        try {
            queue.add(s);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String getTask() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return queue.remove();
    }
}
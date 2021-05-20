package com.atguigu.springcloud.many_thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Package com.atguigu.springcloud.many_thread
 * @ClassName TreadWaitAndNotify
 * @Description 使用wait和notify
 *
 * 在Java程序中，synchronized解决了多线程竞争的问题。例如，对于一个任务管理器，多个线程同时往队列中添加任务，可以用synchronized加锁：
 *
 * class TaskQueue {
 *     Queue<String> queue = new LinkedList<>();
 *
 *     public synchronized void addTask(String s) {
 *         this.queue.add(s);
 *     }
 * }
 * 但是synchronized并没有解决多线程协调的问题。
 *
 * 小结：
 * wait和notify用于多线程协调运行：
 * 在synchronized内部可以调用wait()使线程进入等待状态；
 * 必须在已获得的锁对象上调用wait()方法；
 * 在synchronized内部可以调用notify()或notifyAll()唤醒其他等待线程；
 * 必须在已获得的锁对象上调用notify()或notifyAll()方法；
 * 已唤醒的线程还需要重新获得锁后才能继续执行
 **/
public class TreadWaitAndNotify {
    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    /**
    * 这个例子中，我们重点关注addTask()方法，内部调用了this.notifyAll()而不是this.notify()，使用notifyAll()将唤醒所有当前正在this锁等待的线程，而notify()只会唤醒其中一个（具体哪个依赖操作系统，有一定的随机性）。这是因为可能有多个线程正在getTask()方法内部的wait()中等待，使用notifyAll()将一次性全部唤醒。通常来说，notifyAll()更安全。有些时候，如果我们的代码逻辑考虑不周，用notify()会导致只唤醒了一个线程，而其他线程可能永远等待下去醒不过来了。
     * 但是，注意到wait()方法返回时需要重新获得this锁。假设当前有3个线程被唤醒，唤醒后，首先要等待执行addTask()的线程结束此方法后，才能释放this锁，随后，这3个线程中只能有一个获取到this锁，剩下两个将继续等待。
     * 再注意到我们在while()循环中调用wait()，而不是if语句：
     *
     * public synchronized String getTask() throws InterruptedException {
     *     if (queue.isEmpty()) {
     *         this.wait();
     *     }
     *     return queue.remove();
     * }
     * 这种写法实际上是错误的，因为线程被唤醒时，需要再次获取this锁。多个线程被唤醒后，只有一个线程能获取this锁，此刻，该线程执行queue.remove()可以获取到队列的元素，然而，剩下的线程如果获取this锁后执行queue.remove()，此刻队列可能已经没有任何元素了，所以，要始终在while循环中wait()，并且每次被唤醒后拿到this锁就必须再次判断：
     *
     * while (queue.isEmpty()) {
     *     this.wait();
     * }
     * 所以，正确编写多线程代码是非常困难的，需要仔细考虑的条件非常多，任何一个地方考虑不周，都会导致多线程运行时不正常。
    */
    public static void test1 () throws InterruptedException{
        TaskQueue q = new TaskQueue();
        List<Thread> ts = new ArrayList<Thread>();
        for (int i=0; i<5; i++) {
            Thread t = new Thread() {
                public void run() {
                    // 执行task:
                    while (true) {
                        try {
                            String s = q.getTask();
                            System.out.println("execute task: " + s);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            };
            t.start();
            ts.add(t);
        }
        Thread add = new Thread(() -> {
            for (int i=0; i<10; i++) {
                // 放入task:
                String s = "t-" + Math.random();
                System.out.println("add task: " + s);
                q.addTask(s);
                try { Thread.sleep(100); } catch(InterruptedException e) {}
            }
        });
        add.start();
        add.join();
        Thread.sleep(100);
        for (Thread t : ts) {
            t.interrupt();
        }
    }
}

/**
* 以TaskQueue为例，我们再编写一个getTask()方法取出队列的第一个任务
*/
class TaskQueue {
    Queue<String> queue = new LinkedList<>();

    /**
    * 当一个线程在this.wait()等待时，它就会释放this锁，从而使得其他线程能够在addTask()方法获得this锁。
     * 现在我们面临第二个问题：如何让等待的线程被重新唤醒，然后从wait()方法返回？答案是在相同的锁对象上调用notify()方法。我们修改addTask()如下
     *
     * 注意到在往队列中添加了任务后，线程立刻对this锁对象调用notify()方法，这个方法会唤醒一个正在this锁等待的线程
     * （就是在getTask()中位于this.wait()的线程），从而使得等待线程从this.wait()方法返回。
    */
    public synchronized void addTask(String s) {
        this.queue.add(s);
//        this.notify(); //唤醒在this锁等待的线程
        this.notifyAll();
    }

    /**
    * 代码看上去没有问题：getTask()内部先判断队列是否为空，如果为空，就循环等待，直到另一个线程往队列中放入了一个任务，while()循环退出，就可以返回队列的元素了。
     * 但实际上while()循环永远不会退出。因为线程在执行while()循环时，已经在getTask()入口获取了this锁，其他线程根本无法调用addTask()，因为addTask()执行条件也是获取this锁。
     * 因此，执行上述代码，线程会在getTask()中因为死循环而100%占用CPU资源。
     *
     * 如果深入思考一下，我们想要的执行效果是：
     *
     * 线程1可以调用addTask()不断往队列中添加任务；
     * 线程2可以调用getTask()从队列中获取任务。如果队列为空，则getTask()应该等待，直到队列中至少有一个任务时再返回。
     * 因此，多线程协调运行的原则就是：当条件不满足时，线程进入等待状态；当条件满足时，线程被唤醒，继续执行任务。
    */
    /*public synchronized String getTask() {
        while (queue.isEmpty()) {
        }
        return queue.remove();
    }*/

    /**
    * 改造getTask()方法，在条件不满足时，线程进入等待状态
     *
     * 当一个线程执行到getTask()方法内部的while循环时，它必定已经获取到了this锁，此时，线程执行while条件判断，如果条件成立（队列为空），线程将执行this.wait()，进入等待状态。
     * 这里的关键是：wait()方法必须在当前获取的锁对象上调用，这里获取的是this锁，因此调用this.wait()。
     * 调用wait()方法后，线程进入等待状态，wait()方法不会返回，直到将来某个时刻，线程从等待状态被其他线程唤醒后，wait()方法才会返回，然后，继续执行下一条语句。
     *
     * 有些仔细的童鞋会指出：即使线程在getTask()内部等待，其他线程如果拿不到this锁，照样无法执行addTask()，肿么办？
     *
     * 这个问题的关键就在于wait()方法的执行机制非常复杂。首先，它不是一个普通的Java方法，而是定义在Object类的一个native方法，也就是由JVM的C代码实现的。其次，必须在synchronized块中才能调用wait()方法，因为wait()方法调用时，会释放线程获得的锁，wait()方法返回后，线程又会重新试图获得锁。
     *
     * 因此，只能在锁对象上调用wait()方法。因为在getTask()中，我们获得了this锁，因此，只能在this对象上调用wait()方法
    */
    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            //释放this锁
            this.wait();
            //重新获取this锁
        }
        return queue.remove();
    }
}
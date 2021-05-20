package com.atguigu.springcloud.many_thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Package com.atguigu.springcloud.many_thread
 * @ClassName CreateThread
 * @Description 创建新线程
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/19 10:54
 * @Version 1.0
 **/
@Slf4j
public class CreateThread {
    public static void main(String[] args) {
        //方式一：继承Thread
        Thread t1 = new MyThread();
        //方式二：实现Runnable接口
        Thread t2 = new Thread(new MyRunnable());
        t1.start();
        t2.start();
        //方式三：通过Callable和Future创建线程
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> ft = new FutureTask<>(myCallable);
        for(int i = 0;i < 100;i++)
        {
            log.info(Thread.currentThread().getName()+" 的循环变量i的值"+i);
            if(i==20)
            {
                new Thread(ft,"有返回值的线程").start();
            }
        }
        try
        {
            log.info("子线程的返回值："+ft.get());
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

    }
}

class MyThread extends Thread{
    @Override
    public void run () {
        System.out.println("start new thread! by Thread");
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("start new thread! by Runnable");
    }
}

@Slf4j
class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int i = 0;
        for(;i<100;i++)
        {
            log.info(Thread.currentThread().getName()+" "+i);
        }
        return i;
    }
}
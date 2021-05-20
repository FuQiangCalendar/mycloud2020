package com.atguigu.springcloud.many_thread;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Package com.atguigu.springcloud.many_thread
 * @ClassName ThreadForkJoin
 * @Description 使用Fork/join 框架
 *
 * Java 7开始引入了一种新的Fork/Join线程池，它可以执行一种特殊的任务：把一个大任务拆成多个小任务并行执行。
 * 我们举个例子：如果要计算一个超大数组的和，最简单的做法是用一个循环在一个线程内完成：
 *
 * ┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐
 * └─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘
 * 还有一种方法，可以把数组拆成两部分，分别计算，最后加起来就是最终结果，这样可以用两个线程并行执行：
 *
 * ┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐
 * └─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘
 * ┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐
 * └─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘
 * 如果拆成两部分还是很大，我们还可以继续拆，用4个线程并行执行：
 *
 * ┌─┬─┬─┬─┬─┬─┐
 * └─┴─┴─┴─┴─┴─┘
 * ┌─┬─┬─┬─┬─┬─┐
 * └─┴─┴─┴─┴─┴─┘
 * ┌─┬─┬─┬─┬─┬─┐
 * └─┴─┴─┴─┴─┴─┘
 * ┌─┬─┬─┬─┬─┬─┐
 * └─┴─┴─┴─┴─┴─┘
 * 这就是Fork/Join任务的原理：判断一个任务是否足够小，如果是，直接计算，否则，就分拆成几个小任务分别计算。这个过程可以反复“裂变”成一系列小任务。
 *
 * gitee 地址：https://gitee.com/liaoxuefeng/learn-java/blob/master/practices/Java%E6%95%99%E7%A8%8B/130.%E5%A4%9A%E7%BA%BF%E7%A8%8B.1255943750561472/190.%E4%BD%BF%E7%94%A8ForkJoin.1306581226487842/thread-forkjoin.zip?utm_source=blog_lxf
 *
 * 小结：
 * Fork/Join是一种基于“分治”的算法：通过分解任务，并行执行，最后合并结果得到最终结果。
 * ForkJoinPool线程池可以把一个大任务分拆成小任务并行执行，任务类必须继承自RecursiveTask或RecursiveAction。
 * 使用Fork/Join模式可以进行并行计算以提高效率。
 *
 * 通常使用RecursiveAction（无返回值，实现了Runnable接口），RecursiveTask（有返回值，实现Callable 接口）
 *
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/20 13:13
 * @Version 1.0
 **/
public class ThreadForkJoin {

    public static void main(String[] args) throws Exception {
        test1();
    }

    /**
    * 代码的执行过程，一个大的计算任务0~2000首先分裂为两个小任务0~1000和1000~2000，这两个小任务仍然太大，继续分裂为更小的0~500，500~1000，1000~1500，1500~2000，最后，计算结果被依次合并，得到最终结果。
     * 因此，核心代码SumTask继承自RecursiveTask，在compute()方法中，关键是如何“分裂”出子任务并且提交子任务：
     *
     * class SumTask extends RecursiveTask<Long> {
     *     protected Long compute() {
     *         // “分裂”子任务:
     *         SumTask subtask1 = new SumTask(...);
     *         SumTask subtask2 = new SumTask(...);
     *         // invokeAll会并行运行两个子任务:
     *         invokeAll(subtask1, subtask2);
     *         // 获得子任务的结果:
     *         Long subresult1 = subtask1.join();
     *         Long subresult2 = subtask2.join();
     *         // 汇总结果:
     *         return subresult1 + subresult2;
     *     }
     * }
     *
     * Fork/Join线程池在Java标准库中就有应用。Java标准库提供的java.util.Arrays.parallelSort(array)可以进行并行排序，它的原理就是内部通过Fork/Join对大数组分拆进行并行排序，在多核CPU上就可以大大提高排序的速度。
    */
    public static void test1 () throws Exception {
        // 创建2000个随机数组成的数组:
        long[] array = new long[2000];
        long expectedSum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
            expectedSum += array[i];
        }
        System.out.println("Expected sum: " + expectedSum);
        // fork/join:
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }
}


class SumTask extends RecursiveTask<Long> {
    static final int THRESHOLD = 500;
    long[] array;
    int start;
    int end;

    SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += this.array[i];
                // 故意放慢计算速度:
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);
        invokeAll(subtask1, subtask2);
        Long subresult1 = subtask1.join();
        Long subresult2 = subtask2.join();
        Long result = subresult1 + subresult2;
        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
        return result;
    }
}

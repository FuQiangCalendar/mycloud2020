package com.atguigu.springcloud.algorithm.basic.class1;

/**
 * @author FuQiangCalendar
 * @title: SumOfFactorial
 * @projectName cloud2020
 * @description: 阶乘综合
 * @date 2021/9/30 9:14
 *
 * 1! + 2! + 3! + ... + n!
 */
public class SumOfFactorial {

    public static void main(String[] args) {
        /**N <= 1000 f1 优于 f2*/
        int N = 9999;
        long f1_start = System.currentTimeMillis();
        System.out.println(f1(N));
        long f2_start = System.currentTimeMillis();
        System.out.println(String.format("f1 耗时 %s 毫秒", f2_start - f1_start));
        System.out.println(f2(N));
        System.out.println(String.format("f2 耗时 %s 毫秒", System.currentTimeMillis() - f2_start));
    }

    /**
     * @title f1
     * @description  1*1 + (1*1*2) + (1*1*2*3) + (1*1*2*3*4)....
     * @author FuQiangCalendar
     * @param: N
     * @updateTime 2021/9/30 9:16
     * @return: long
     * @throws
     */
    public static long f1(int N) {
        long ans = 0;
        for (int i = 1; i <= N; i++) {
            ans += factorial(i);
        }
        return ans;
    }

    public static long factorial(int N) {
        long ans = 1;
        for (int i = 1; i <= N; i++) {
            ans *= i;
        }
        return ans;
    }

    /**
     * @title f2
     * @description 1！ + 1！*2 + 2！*3 + 3！*4 ...
     * @author FuQiangCalendar
     * @param: N
     * @updateTime 2021/9/30 9:17
     * @return: long
     * @throws
     */
    public static long f2(int N) {
        long ans = 0;
        long cur = 1;
        for (int i = 1; i <= N; i++) {
            cur = cur * i;
            ans += cur;
        }
        return ans;
    }
}

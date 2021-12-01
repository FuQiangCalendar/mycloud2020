package com.atguigu.springcloud.algorithm.basic.class1;

import java.util.Arrays;

/**
 * @author FuQiangCalendar
 * @title: Sort
 * @projectName cloud2020
 * @description: 排序
 * @date 2021/9/30 9:44
 */
public class Sort {

    public static void main(String[] args) {
        int[] arr = { 7, 1, 3, 5, 1, 6, 8, 1, 3, 5, 7, 5, 6 };
        printArray(arr);
        insertSort2(arr);
        printArray(arr);

        int bigArrLen = 100000;
        int[] bigArr = new int[bigArrLen];

        for (int i = 0; i < bigArrLen; i++) {
            bigArr[i] = (int) Math.round(Math.random()*bigArrLen);
        }
        int[] bubbleSortArr = bigArr.clone();
        int[] insertSortArr = bigArr.clone();
        long t1 = System.currentTimeMillis();
        selectSort(bigArr);
        System.out.println(String.format("selectSort 耗时 %s 毫秒", System.currentTimeMillis() - t1));
        long t2 = System.currentTimeMillis();
        bubbleSort(bubbleSortArr);
        System.out.println(String.format("bubbleSort 耗时 %s 毫秒", System.currentTimeMillis() - t2));
        long t3 = System.currentTimeMillis();
        insertSort2(insertSortArr);
        System.out.println(String.format("insertSort 耗时 %s 毫秒", System.currentTimeMillis() - t3));

        test();

    }

    /**
     * @title selectSort
     * @description 选择排序
     * 0~N-1中找到最小值，和0 位进行交换
     * 1~N-1 中找到最小值，和1 位进行交换
     * ...
     * N-2~N-1 中找到最小值，和N-2 位进行交换
     * 时间复杂分析：
     * 分析最小动作，即 看+比， 一次换
     * 常数级别操作
     * N*(看+比) + 一次交换
     * --------------------
     * （N-1)*(2) + 1
     * ...
     * (N-2)*2 +1
     * 从上可看，是一个等差数列，可化简为 2*(N+ N-1 + ... +1) +N
     * 即可写为 aN^2 + bN +c (a,b,c都是常数)
     * 根据时间复杂度，不看低阶项和高阶项系数，可得O(N^2)
     *
     * 确定算法流程的总操作数量与样本数量之间的表达式关系
     * 1、想象该算法流程所处理的数据状况，要按照最差情况来。
     * 2、把整个流程彻底拆分为一个个基本动作，保证每个动作都是常数时间的操作。
     * 3、如果数据为N，看看基本动作的数量和N是什么关系。
     *
     * 如何确定算法流程的时间复杂度？
     * 当完成了表达式的建立，只要把最高阶项留下即可。低阶项都去掉，
     * 高阶项的系数也去掉。记为：O(忽略掉系数的高阶项)
     *
     *
     * @author FuQiangCalendar
     * @param: arr
     * @updateTime 2021/9/30 9:46
     * @throws
     */
    public static void selectSort (int[] arr) {
        if (null == arr || arr.length < 2) {
            return;
        }

        for (int i=0, n = arr.length; i < n; i++) {
            int minValueIndex = i;
            for (int j = i + 1; j < n; j++) {
                minValueIndex = arr[j] < arr[minValueIndex] ? j : minValueIndex;
            }
            swap(arr, i, minValueIndex);
        }

    }

    /**
     * @title swap
     * @description 数组中两个位置的值进行交换
     * @author FuQiangCalendar
     * @param: arr
     * @param: i
     * @param: j
     * @updateTime 2021/9/30 9:53
     * @throws
     */
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[j];
        arr[j] = arr[i];
        arr[i] = tmp;
    }

    /**
     * @title bubbleSort
     * @description 冒泡排序
     * 有 n个数的数组， 进行n-1轮筛选
     * 1轮 0 1 位比较，那个大，往右移， 1 2 位比较....
     * n-1 轮
     * 时间复杂都分析：
     * 0~N-1 比较和交换
     * 0~N-2 比较和交换
     * ...
     * N-1 * (看+比+交换)
     * N-2 * （看+比+交换）
     * ...
     * (N-1 + N-2 + ... + 1) * 3
     *
     *等差数列O(N^2)
     * @author FuQiangCalendar
     * @param: arr
     * @updateTime 2021/9/30 9:55
     * @throws
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int end = N - 1; end >= 0; end--) {  // 可调整为 end >= 1
            for (int second = 1; second <= end; second++) {
                if (arr[second - 1] > arr[second]) {
                    swap(arr, second - 1, second);
                }
            }
        }
    }

    /**
     * @title insertSort1
     * @description 插入排序
     * 有 n个数的数组，进行n-1 轮比对
     * 第1轮 ： 0，1 比较
     * 第2轮：2和0,1比较
     * 第三轮： 3和0,1,2 比较
     *
     * 时间复杂度分析：
     * 1和0 看+比+交换
     * 2 和0,1 看+比+交换
     * 3 和 0,1,2 看+比+交换
     * ...
     * N-1 和 0~N-2 看+比+交换
     *
     * 1*3 + 2*3 + ... + (N-1)*3
     * 等差数列，O(N^2)
     *
     * @author FuQiangCalendar
     * @param: arr
     * @updateTime 2021/9/30 10:09
     * @throws
     */
    public static void insertSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int end = 1; end < N; end++) {
            int newNumIndex = end;
            while (newNumIndex - 1 >= 0 && arr[newNumIndex - 1] > arr[newNumIndex]) {
                swap(arr, newNumIndex - 1, newNumIndex);
                newNumIndex--;
            }
        }
    }

    public static void insertSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int end = 1; end < N; end++) {
            for (int pre = end - 1; pre >= 0 && arr[pre] > arr[pre + 1]; pre--) {
                swap(arr, pre, pre + 1);
            }
        }
    }



    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 样本产生器
    public static int[] generateRandomArray (int maxSize,int maxValue) {
        // Math.random() [0,1)
        // Math.random() * N [0,N)
        // (Math.ramdom() * N) [0, N-1]
        int[] arr = new int[(int) ((maxSize+1)*Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static void comparator (int[] arr) {
        Arrays.sort(arr);
    }

    // 对数器
    public static void test () {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int [] arr1 = generateRandomArray(maxSize, maxValue);
            int [] arr2 = copyArray(arr1);
            selectSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }

        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null)
                || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}

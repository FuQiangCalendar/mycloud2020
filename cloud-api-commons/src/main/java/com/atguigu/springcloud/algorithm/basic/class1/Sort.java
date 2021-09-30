package com.atguigu.springcloud.algorithm.basic.class1;

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

    }

    /**
     * @title selectSort
     * @description 选择排序
     * 0~N-1中找到最小值，和0 位进行交换
     * 1~N-1 中找到最小值，和1 位进行交换
     * ...
     * N-2~N-1 中找到最小值，和N-2 位进行交换
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
}

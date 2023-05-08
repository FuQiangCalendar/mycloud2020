package com.atguigu.springcloud.algorithm.basic.class2;

/**
 * @Name Code04_BSExist
 * @Description 二分法
 * 局部最小
 * arr [...] 0, ..., N-1 无相同的
 * 0 位置大于1 位置的，下走
 * n-1 位置大于n-2位置的，上走，
 * 由此可见，必有局部最小
 * @Author qfu1
 * @Date 2021-11-30
 */
public class Code04_BSExist {

    public static void main(String[] args) {
        test();
    }

    // 对数器
    public static void test () {
        int testTime = 5000;
        int maxSize = 100000;
        int maxValue = 10000;
        for (int i = 0; i < testTime; i++) {
            int [] arr1 = generateRandomArray(maxSize, maxValue);
            long startTime = System.currentTimeMillis();
            int checkNum = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (!exist(arr1, checkNum)) {
//                System.out.println(String.format("无 %s", checkNum));
            }else {
                System.out.println(String.format("有 %s", checkNum));
                System.out.println(System.currentTimeMillis() - startTime + "ms");
            }
        }
    }

    public static boolean exist (int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return false;
        }

        int L = 0;
        int R = sortedArr.length -1;
        int mid = 0;

        while (L < R) {
            // mid = (L + R) / 2
            // L 10亿  R 18亿  容易溢出不安全
            // mid = L + (R - L) /2 = (2L + R - L)/2 = (R+L)/2
            // N / 2  N >> 1  位运算更快
            // N*2+1  ==  ((N<<1) | 1)
            mid = L + ((R - L ) >> 1); // mid = (L + R)/2
            if (sortedArr[mid] == num) {
                return true;
            }else if (sortedArr[mid] > num){
                R = mid - 1;
            }else {
                L = mid + 1;
            }
        }
        return sortedArr[L] == num;
    }

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
}

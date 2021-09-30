package com.atguigu.springcloud.algorithm.basic.class1;

/**
 * @author FuQiangCalendar
 * @title: BitOperation
 * @projectName cloud2020
 * @description: 位运算
 * @date 2021/9/30 8:42
 */
public class BitOperation {

    public static void main(String[] args) {
        // 32位
		//int num = 4;
        //
		//print(num);
        //
        //
		//int test = 1123123;
		//print(test);
		//print(test<<1);
		//print(test<<2);
		//print(test<<8);


		//int a = Integer.MAX_VALUE;
		//System.out.println(a);
        //
		///**0~30位 取反加1，再加上符号*/
		//print(-1);
		////int a = Integer.MIN_VALUE;
		////print(a);
        //
		//int b = 123823138;
		//int c = ~b;
		//print(b);
		//print(c);
        //
		//c = (~b + 1);
        //System.out.println("b 取反加1");
        //print(c);
		//print(-5);
        //
		//System.out.println(Integer.MIN_VALUE);
		//System.out.println(Integer.MAX_VALUE);

		//int a = 12319283;
		//int b = 3819283;
		//print(a);
		//print(b);
		//System.out.println("=============");
		//print(a | b);  //只要有一个为1，则为1，否则0
		//print(a & b);  //两个都为1, 则为1，否则为0
		//print(a ^ b);  //两个都为0，则为1，否则0

		int a = Integer.MIN_VALUE;
		print(a);
		print(a >> 1);  // 带符号右移，左边用符号填充
		print(a >>> 1);   //不带符号右移，左边用0填充

		int c = Integer.MIN_VALUE;  //最小值和0取反，依然是自己
		int d = -c ;

		print(c);
		print(d);
    }

    /**
     * @title print
     * @description 打印int 类型数字的二进制
     * @author FuQiangCalendar
     * @param: num
     * @updateTime 2021/9/30 8:50
     * @throws
     * @Tans:
     *      1、第31位 为 符号占位符， 非负数为0， 负数为1
     *      2、负数和正数的运算逻辑是一套，因为，在转换时，先获取符号标记， 再处理0~30位的。
     *          a、正数,0~30;   (0/1)2^0 + ...+ (0/1)2^30
     *          b、负数， 先把0~30位，取反，即0变1,1变0； 例如：11011 →  00100
     *                    再加1，若0位本为1，则变0，进一为，加1，依次类推
     *                    最后把符号加上
     *
     */
    public static void print (int num) {

        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0  ? "0" : "1");
        }
        System.out.println();
    }
}

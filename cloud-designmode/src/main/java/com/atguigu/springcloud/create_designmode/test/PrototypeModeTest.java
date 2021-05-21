package com.atguigu.springcloud.create_designmode.test;

import lombok.Data;

/**
 * @Package com.atguigu.springcloud.create_designmode.test
 * @ClassName PrototypeModeTest
 * @Description 创建模型 -- 原型
 *
 * 用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。
 * 原型模式，即Prototype，是指创建新对象的时候，根据现有的一个原型来创建。
 * 我们举个例子：如果我们已经有了一个String[]数组，想再创建一个一模一样的String[]数组，怎么写？
 * 实际上创建过程很简单，就是把现有数组的元素复制到新数组。如果我们把这个创建过程封装一下，就成了原型模式。用代码实现如下：
 *
 * // 原型:
 * String[] original = { "Apple", "Pear", "Banana" };
 * // 新对象:
 * String[] copy = Arrays.copyOf(original, original.length);
 * 对于普通类，我们如何实现原型拷贝？Java的Object提供了一个clone()方法，它的意图就是复制一个新的对象出来，我们需要实现一个Cloneable接口来标识一个对象是“可复制”的
 *
 * 原型模式应用不是很广泛，因为很多实例会持有类似文件、Socket这样的资源，而这些资源是无法复制给另一个对象共享的，只有存储简单类型的“值”对象可以复制。
 *
 * 小结：
 * 原型模式是根据一个现有对象实例复制出一个新的实例，复制出的类型和属性与原实例相同。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/20 17:33
 * @Version 1.0
 **/
public class PrototypeModeTest {

    public static void main(String[] args) {
        test1();
    }

    /**
    * @Description : 使用的时候，因为clone()的方法签名是定义在Object中，返回类型也是Object，
     * 所以要强制转型，比较麻烦：
    */
    public static void test1 () {
        Student std1 = new Student();
        std1.setId(123);
        std1.setName("Bob");
        std1.setScore(88);
        // 复制新对象:
        Student std2 = (Student) std1.clone();
        System.out.println(std1);
        System.out.println(std2);
        System.out.println(std1 == std2); // false
    }
}

@Data
class Student implements Cloneable {
    private int id;
    private String name;
    private int score;

    // 复制新对象并返回:
    @Override
    public Object clone() {
        Student std = new Student();
        std.id = this.id;
        std.name = this.name;
        std.score = this.score;
        return std;
    }
}

/**
* @Description : 实际上，使用原型模式更好的方式是定义一个copy()方法，返回明确的类型
 *
 *
*/
class Student2 {
    private int id;
    private String name;
    private int score;

    public Student2 copy() {
        Student2 std = new Student2();
        std.id = this.id;
        std.name = this.name;
        std.score = this.score;
        return std;
    }
}
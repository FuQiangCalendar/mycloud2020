package com.atguigu.springcloud.behavior_designmode.test;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @Package com.atguigu.springcloud.behavior_designmode.test
 * @ClassName InterpreterModeTest
 * @Description 行为型模式 -- 解释器
 *
 * 给定一个语言，定义它的文法的一种表示，并定义一个解释器，这个解释器使用该表示来解释语言中的句子。
 * 解释器模式（Interpreter）是一种针对特定问题设计的一种解决方案。例如，匹配字符串的时候，由于匹配条件非常灵活，使得通过代码来实现非常不灵活。举个例子，针对以下的匹配条件：
 *
 * 以+开头的数字表示的区号和电话号码，如+861012345678；
 * 以英文开头，后接英文和数字，并以.分隔的域名，如www.liaoxuefeng.com；
 * 以/开头的文件路径，如/path/to/file.txt；
 * ...
 * 因此，需要一种通用的表示方法——正则表达式来进行匹配。正则表达式就是一个字符串，但要把正则表达式解析为语法树，然后再匹配指定的字符串，就需要一个解释器。
 * 实现一个完整的正则表达式的解释器非常复杂，但是使用解释器模式却很简单：
 *
 * String s = "+861012345678";
 * System.out.println(s.matches("^\\+\\d+$"));
 * 类似的，当我们使用JDBC时，执行的SQL语句虽然是字符串，但最终需要数据库服务器的SQL解释器来把SQL“翻译”成数据库服务器能执行的代码，这个执行引擎也非常复杂，但对于使用者来说，仅仅需要写出SQL字符串即可。
 *
 * 小结：
 * 解释器模式通过抽象语法树实现对用户输入的解释执行。
 * 解释器模式的实现通常非常复杂，且一般只能解决一类特定问题。
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/21 17:26
 * @Version 1.0
 **/
@Slf4j
public class InterpreterModeTest {

    public static void main(String[] args) {
        test1();
    }

    /**
    * @Description :请实现一个简单的解释器，它可以以SLF4J的日志格式输出字符串
    *  [15:21:33] start engine at 2021-05-24...
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/24 15:20
    */
    public static void test1 () {
        log.info("[{}] start {} at {}...", LocalTime.now().withNano(0), "engine", LocalDate.now());
    }
}

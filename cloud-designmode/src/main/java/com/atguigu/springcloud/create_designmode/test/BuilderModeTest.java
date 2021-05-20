package com.atguigu.springcloud.create_designmode.test;

import com.atguigu.springcloud.create_designmode.service.builder.HtmlBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Package com.atguigu.springcloud.create_designmode.test
 * @ClassName BuilderModeTest
 * @Description 创建模式-- 生成器
 *
 * 将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * 生成器模式（Builder）是使用多个“小型”工厂来最终创建出一个完整对象。
 * 当我们使用Builder的时候，一般来说，是因为创建这个对象的步骤比较多，每个步骤都需要一个零部件，最终组合成一个完整的对象。
 * 我们仍然以Markdown转HTML为例，因为直接编写一个完整的转换器比较困难，但如果针对类似下面的一行文本
 *
 * 小结：
 * Builder模式是为了创建一个复杂的对象，需要多个步骤完成创建，或者需要多个零件组装的场景，且创建过程中可以灵活调用不同的步骤或组件。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/20 16:50
 * @Version 1.0
 **/
@Slf4j
public class BuilderModeTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1 () {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        String s = htmlBuilder.toHtml("# this is a heading");
        log.info("html : " + s);
    }
}

package com.atguigu.springcloud.create_designmode.test;

import com.atguigu.springcloud.create_designmode.service.abstracts.AbstractFactory;
import com.atguigu.springcloud.create_designmode.service.abstracts.HtmlDocument;
import com.atguigu.springcloud.create_designmode.service.abstracts.WordDocument;
import com.atguigu.springcloud.create_designmode.service.abstracts.impl.FastFactory;
import com.atguigu.springcloud.create_designmode.service.abstracts.impl.GoodFactory;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;

/**
 * @Package com.atguigu.springcloud.create_designmode.test
 * @ClassName AbstractFactoryTest
 * @Description 抽象工厂测试类
 * 提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。
 * 抽象工厂模式（Abstract Factory）是一个比较复杂的创建型模式。
 * 抽象工厂模式和工厂方法不太一样，它要解决的问题比较复杂，不但工厂是抽象的，产品是抽象的，而且有多个产品需要创建，因此，这个抽象工厂会对应到多个实际工厂，每个实际工厂负责创建多个实际产品：
 *
 *                                 ┌────────┐
 *                              ─ >│ProductA│
 * ┌────────┐    ┌─────────┐   │   └────────┘
 * │ Client │─ ─>│ Factory │─ ─
 * └────────┘    └─────────┘   │   ┌────────┐
 *                    ▲         ─ >│ProductB│
 *            ┌───────┴───────┐    └────────┘
 *            │               │
 *       ┌─────────┐     ┌─────────┐
 *       │Factory1 │     │Factory2 │
 *       └─────────┘     └─────────┘
 *            │   ┌─────────┐ │   ┌─────────┐
 *             ─ >│ProductA1│  ─ >│ProductA2│
 *            │   └─────────┘ │   └─────────┘
 *                ┌─────────┐     ┌─────────┐
 *            └ ─>│ProductB1│ └ ─>│ProductB2│
 *                └─────────┘     └─────────┘
 * 这种模式有点类似于多个供应商负责提供一系列类型的产品
 *
 * 这样，我们就定义好了抽象工厂（AbstractFactory）以及两个抽象产品（HtmlDocument和WordDocument）。因为实现它们比较困难，我们决定让供应商来完成。
 * 现在市场上有两家供应商：FastDoc Soft的产品便宜，并且转换速度快，而GoodDoc Soft的产品贵，但转换效果好。我们决定同时使用这两家供应商的产品，以便给免费用户和付费用户提供不同的服务。
 * 我们先看看FastDoc Soft的产品是如何实现的。首先，FastDoc Soft必须要有实际的产品，即FastHtmlDocument和FastWordDocument
 *
 * 小结：
 * 抽象工厂模式是为了让创建工厂和一组产品与使用相分离，并可以随时切换到另一个工厂以及另一组产品；
 * 抽象工厂模式实现的关键点是定义工厂接口和产品接口，但如何实现工厂与产品本身需要留给具体的子类实现，客户端只和抽象工厂与抽象产品打交道
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/20 15:59
 * @Version 1.0
 **/
@Slf4j
public class AbstractFactoryTest {

    public static void main(String[] args) throws Exception  {
        test1();
        test2();
        test3();
    }

    public static void test1 () throws Exception {
        // 创建AbstractFactory，实际类型是FastFactory:
        AbstractFactory factory = new FastFactory();
        // 生成Html文档:
        HtmlDocument html = factory.createHtml("#Hello\nHello, world!");
        html.save(Paths.get(".", "fast.html"));
        // 生成Word文档:
        WordDocument word = factory.createWord("#Hello\nHello, world!");
        word.save(Paths.get(".", "fast.doc"));
    }

    public static void test2 () throws Exception{
        // 创建AbstractFactory，实际类型是FastFactory:
        AbstractFactory factory = new GoodFactory();
        // 生成Html文档:
        HtmlDocument html = factory.createHtml("#Hello\nHello, world!");
        html.save(Paths.get(".", "fast.html"));
        // 生成Word文档:
        WordDocument word = factory.createWord("#Hello\nHello, world!");
        word.save(Paths.get(".", "fast.doc"));
    }

    public static void test3 () throws Exception {
        // 创建AbstractFactory，实际类型是FastFactory:
//        AbstractFactory factory = AbstractFactory.createFactory(FastFactory.class);
        AbstractFactory factory = AbstractFactory.createFactory("fast");
        // 生成Html文档:
        HtmlDocument html = factory.createHtml("#Hello\nHello, world!");
        html.save(Paths.get(".", "fast.html"));
        // 生成Word文档:
        WordDocument word = factory.createWord("#Hello\nHello, world!");
        word.save(Paths.get(".", "fast.doc"));
    }
}

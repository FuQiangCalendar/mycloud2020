package com.atguigu.springcloud.create_designmode.service.abstracts;

import com.atguigu.springcloud.create_designmode.service.abstracts.impl.FastFactory;
import com.atguigu.springcloud.create_designmode.service.abstracts.impl.GoodFactory;

public interface AbstractFactory {
    // 创建Html文档:
    HtmlDocument createHtml(String md);

    // 创建Word文档:
    WordDocument createWord(String md);

    /**
     * 注意到客户端代码除了通过new创建了FastFactory或GoodFactory外，其余代码只引用了产品接口，并未引用任何实际产品（例如，FastHtmlDocument），如果把创建工厂的代码放到AbstractFactory中，就可以连实际工厂也屏蔽了
     */
    static AbstractFactory createFactory(String name) {
        if (name.equalsIgnoreCase("fast")) {
            return new FastFactory();
        } else if (name.equalsIgnoreCase("good")) {
            return new GoodFactory();
        } else {
            throw new IllegalArgumentException("Invalid factory name");
        }
    }

    static AbstractFactory createFactory(Class obj) throws Exception {
//        Object o = obj.newInstance();
        if (obj == FastFactory.class) {
            return new FastFactory();
        } else if (obj == GoodFactory.class) {
            return new GoodFactory();
        } else {
            throw new IllegalArgumentException("Invalid factory name");
        }
    }
}
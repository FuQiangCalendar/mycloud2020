package com.atguigu.springcloud.create_designmode.service.abstracts.impl;

import com.atguigu.springcloud.create_designmode.service.abstracts.AbstractFactory;
import com.atguigu.springcloud.create_designmode.service.abstracts.HtmlDocument;
import com.atguigu.springcloud.create_designmode.service.abstracts.WordDocument;

/**
* 这样，我们就可以使用FastDoc Soft的服务了。客户端编写代码如
*/
public class FastFactory implements AbstractFactory {
    @Override
    public HtmlDocument createHtml(String md) {
        return new FastHtmlDocument(md);
    }
    @Override
    public WordDocument createWord(String md) {
        return new FastWordDocument(md);
    }
}
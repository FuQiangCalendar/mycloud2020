package com.atguigu.springcloud.create_designmode.service.abstracts.impl;

import com.atguigu.springcloud.create_designmode.service.abstracts.AbstractFactory;
import com.atguigu.springcloud.create_designmode.service.abstracts.HtmlDocument;
import com.atguigu.springcloud.create_designmode.service.abstracts.WordDocument;
import lombok.extern.slf4j.Slf4j;

/**
* 客户端要使用GoodDoc Soft的服务，只需要把原来的new FastFactory()切换为new GoodFactory()即可
*/

@Slf4j
public class GoodFactory implements AbstractFactory {
    @Override
    public HtmlDocument createHtml(String md) {
        return new GoodHtmlDocument(md);
    }
    @Override
    public WordDocument createWord(String md) {
        return new GoodWordDocument(md);
    }
}
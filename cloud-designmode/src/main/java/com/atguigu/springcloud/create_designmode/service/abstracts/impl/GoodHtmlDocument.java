package com.atguigu.springcloud.create_designmode.service.abstracts.impl;

import com.atguigu.springcloud.create_designmode.service.abstracts.HtmlDocument;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;

// 实际产品:
@Slf4j
public class GoodHtmlDocument implements HtmlDocument {
    //加final，构造时（初始化）不可少参数
    private final String md;

    public GoodHtmlDocument (String md) {
        this.md = md;
    }

    @Override
    public String toHtml() {
        log.info("GoodHtmlDocument to Html");
        return null;
    }

    @Override
    public void save(Path path) throws IOException {
        log.info("GoodHtmlDocument to save " + path.toString());
    }
}
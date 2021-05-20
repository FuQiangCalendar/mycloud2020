package com.atguigu.springcloud.create_designmode.service.abstracts.impl;

import com.atguigu.springcloud.create_designmode.service.abstracts.HtmlDocument;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class FastHtmlDocument implements HtmlDocument {

    final private String md;

    public FastHtmlDocument (String md) {
        this.md = md;
    }

    @Override
    public String toHtml() {
        log.info("FastHtmlDocument to Html");
        return "FastHtmlDocument to Html";
    }

    @Override
    public void save(Path path) throws IOException {
        log.info("FastHtmlDocument to save" + path.toString());
    }
}
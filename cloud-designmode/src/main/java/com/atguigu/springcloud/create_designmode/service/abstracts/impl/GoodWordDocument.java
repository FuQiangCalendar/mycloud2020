package com.atguigu.springcloud.create_designmode.service.abstracts.impl;


import com.atguigu.springcloud.create_designmode.service.abstracts.WordDocument;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
@Slf4j
public class GoodWordDocument implements WordDocument {

    private final String md;

    public GoodWordDocument (String md) {
        this.md = md;
    }

    @Override
    public void save(Path path) throws IOException {
        log.info("GoodWordDocument to save " + path.toString());
    }
}
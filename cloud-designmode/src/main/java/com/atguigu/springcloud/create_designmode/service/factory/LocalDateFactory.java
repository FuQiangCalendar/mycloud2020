package com.atguigu.springcloud.create_designmode.service.factory;

import com.atguigu.springcloud.create_designmode.service.factory.impl.LocalDateFactoryImpl;

import java.time.LocalDate;

public interface LocalDateFactory {
    LocalDate parse (long num);

    static LocalDateFactory getLocalDateFactory () {
        return localDateFactory;
    }

    static LocalDateFactory localDateFactory = new LocalDateFactoryImpl();
}

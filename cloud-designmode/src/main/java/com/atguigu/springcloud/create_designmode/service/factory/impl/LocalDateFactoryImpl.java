package com.atguigu.springcloud.create_designmode.service.factory.impl;

import com.atguigu.springcloud.create_designmode.service.factory.LocalDateFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * @Package com.atguigu.springcloud.create_designmode.impl
 * @ClassName LocalDateFactory
 * @Description 日期格式化实现
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/20 15:40
 * @Version 1.0
 **/
public class LocalDateFactoryImpl implements LocalDateFactory {

    @Override
    public LocalDate parse(long num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        date.setTime(num);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return LocalDate.of(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH) + 1, instance.get(Calendar.DAY_OF_MONTH));
    }
}

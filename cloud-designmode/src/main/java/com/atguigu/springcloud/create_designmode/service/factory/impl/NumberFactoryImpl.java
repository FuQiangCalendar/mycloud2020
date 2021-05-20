package com.atguigu.springcloud.create_designmode.service.factory.impl;

import com.atguigu.springcloud.create_designmode.service.factory.NumberFactory;

import java.math.BigDecimal;

/**
* @Description : 数字工厂接口实现
*
*/
public class NumberFactoryImpl implements NumberFactory {

    /**
    * @Description : 产品接口是Number，NumberFactoryImpl返回的实际产品是BigDecimal
    */
    public Number parse(String s) {
        return new BigDecimal(s);
    }
}
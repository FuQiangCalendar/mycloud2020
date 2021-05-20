package com.atguigu.springcloud.create_designmode.service.factory;

import com.atguigu.springcloud.create_designmode.service.factory.impl.NumberFactoryImpl;

/**
* @Description : 数字工厂接口
*
*/
public interface NumberFactory {
    Number parse(String s);

    /**
    * 那么客户端如何创建NumberFactoryImpl呢？通常我们会在接口Factory中定义一个静态方法getFactory()
     * 来返回真正的子类
    */
    // 获取工厂实例:
    static NumberFactory getFactory() {
        return impl;
    }

    static NumberFactory impl = new NumberFactoryImpl();
}

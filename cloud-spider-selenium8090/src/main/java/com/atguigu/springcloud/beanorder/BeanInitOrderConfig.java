package com.atguigu.springcloud.beanorder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanInitOrderConfig {


    @Bean(initMethod = "initMethod" ,destroyMethod = "destroyMethod")
    public BeanInitOrder getBeanInitOrder(){
        return new BeanInitOrder();
    }

    @Bean
    public InitBeanHandleBeanPostProcessor getInitBeanHandle(){
        return new InitBeanHandleBeanPostProcessor();
    }

    @Bean
    public static InitBeanHandleBeanFactoryPostProcessor getInitBeanHandleBeanFactoryPostProcessor(){
        return new InitBeanHandleBeanFactoryPostProcessor();
    }

}
package com.atguigu.springcloud.beanorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Name BeanInitOrder
 * @Description
 * @Author qfu1
 * @Date 2022-11-23
 */
public class BeanInitOrder implements InitializingBean,
        BeanFactoryAware, BeanNameAware, ApplicationContextAware, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(BeanInitOrder.class);

    private String name;

    public BeanInitOrder(){
        logger.info("执行{}的构造方法", BeanInitOrder.class.getName());
    }

    public void initMethod() {
        logger.info("执行{}定义的initMethod方法", BeanInitOrder.class.getName());
    }

    public void destoryMethod() {
        logger.info("执行{}定义的destoryMethod方法", BeanInitOrder.class.getName());
    }

    @PostConstruct
    public void postConstruct() {
        logger.info("执行{}的PostConstruct方法", BeanInitOrder.class.getName());
    }

    @PreDestroy
    public void preDestroy() {
        logger.info("执行PreDestroy方法", BeanInitOrder.class.getName());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        logger.info("{}注入BeanFactory==>{}", BeanInitOrder.class.getName(), beanFactory.getClass().getName());
    }

    @Override
    public void setBeanName(String s) {
        logger.info("{}注入BeanName==>{}", BeanInitOrder.class.getName(), s);
    }

    @Override
    public void destroy() throws Exception {
        logger.info("{}执行量DisposableBean的destroy方法", BeanInitOrder.class.getName());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("{}执行InitializingBean的afterPropertiesSet方法", BeanInitOrder.class.getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("{}注入ApplicationContext==>{}", BeanInitOrder.class.getName(), applicationContext.getClass().getName());
        logger.info("{}打印ApplicationContext>>{}", BeanInitOrder.class.getName(), transfApplicationContext(applicationContext));
    }

    private String transfApplicationContext(ApplicationContext applicationContext) {
        StringBuilder sb = new StringBuilder();
        if (null == applicationContext){
            return sb.toString();
        }
        sb.append(applicationContext.getParent().getClass().getName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id =").append(applicationContext.getId());
        sb.append(", applicationName =").append(applicationContext.getApplicationName());
        sb.append(", displayName = ").append(applicationContext.getDisplayName());
        sb.append(", startupDate = ").append(applicationContext.getStartupDate());
        sb.append(", parent = ").append(applicationContext.getParent().getClass().getName());
        sb.append(", autowireCapableBeanFactory").append(applicationContext.getAutowireCapableBeanFactory().getClass().getName());

        if (null != applicationContext.getParent()) {
            sb.append(", parentInfo = ").append(transfApplicationContext(applicationContext.getParent()));
        }
        sb.append("]");

        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

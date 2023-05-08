package com.atguigu.springcloud;

import com.atguigu.springcloud.beanorder.BeanInitOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class seleniumSpiderApplication {

	@Autowired
	private BeanInitOrder beanInitOrder;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(seleniumSpiderApplication.class, args);
	}

	/*@PostConstruct
	public void test(){
		System.out.println(beanInitOrder.getName());
	}*/
}

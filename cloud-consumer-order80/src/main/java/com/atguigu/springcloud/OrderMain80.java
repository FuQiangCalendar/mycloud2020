package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 
 * @author MrFu
 *
 */

@SpringBootApplication
@EnableEurekaClient
public class OrderMain80 {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderMain80.class, args);
	}
}

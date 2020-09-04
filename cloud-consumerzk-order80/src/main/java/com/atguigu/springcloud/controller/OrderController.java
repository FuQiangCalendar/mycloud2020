package com.atguigu.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author MrFu
 *
 */
@RestController
@Slf4j
@RequestMapping("/consumer")
@Api("消费者操作中心")
public class OrderController {
	
//	public static final String PAYMENT_URL = "http://localhost:8001";
	public static final String PAYMENT_URL = "http://cloud-payment-service";
	
	@Resource
	private RestTemplate restTemplate;
	
	@GetMapping("/zk")
	public String payment() {
		return restTemplate.getForObject(PAYMENT_URL+"/payment/zk", String.class);
	}
	
	@GetMapping("/save")
	@ApiOperation("消费者保存记录")
	public CommonResult<Payment> save (Payment payment) {
		log.info("客户端保存");
		return restTemplate.postForObject(PAYMENT_URL+"/payment/save", payment, CommonResult.class);
	}
	
	@GetMapping("/get/{id}")
	@ApiOperation("消费者根据id，查询记录")
	public CommonResult<Payment> getPaymentById (@PathVariable("id") Long id) {
		log.info("客户端查询");
		return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
	}
	
	
	
	
	
	
}

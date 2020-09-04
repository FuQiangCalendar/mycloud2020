package com.atguigu.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

	@Resource
	private PaymentService paymentService;
	
	@Value("${server.port}")
	private String serverport;
	
	@PostMapping("/save")
	@ApiOperation("新增记录")
	public CommonResult save (@RequestBody Payment payment) {
		int result = paymentService.save(payment);
		log.info("****插入结果为："+ result);
		
		if (result > 0) {
			return new CommonResult(200, "插入数据库成功,serverport:"+serverport, result);
		}else {
			return new CommonResult(444, "插入数据库失败,serverport:"+serverport, null);
		}
	}
	
	@GetMapping("/get/{id}")
	@ApiOperation("根据id，查询记录")
	public CommonResult getPaymentById (@PathVariable("id") Long id) {
		Payment payment = paymentService.getPaymentById(id);
		log.info("****查询插入结果为："+ payment);
		log.info("热部署开启了");
		
		if (payment != null) {
			return new CommonResult(200, "查询成功,serverport:"+serverport, payment);
		}else {
			return new CommonResult(444, "没有对应记录，查询ID："+ id+",serverport:"+serverport, null);
		}
	}

}

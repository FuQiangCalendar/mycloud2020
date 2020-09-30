package com.atguigu.springcloud.alipay.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.atguigu.springcloud.alipay.entity.AlipayBean;
import com.atguigu.springcloud.alipay.service.AliPayService;

@RestController
@RequestMapping("pay")
public class AliPayController {
	@Resource
    private AliPayService aliPayService;//调用支付服务
	
    /*阿里支付*/
    @GetMapping(value = "alipay")
    public String alipay(@RequestParam("out_trade_no") String out_trade_no, @RequestParam("subject") String subject,
    		@RequestParam("total_amount") String total_amount, @RequestParam("body") String body) throws AlipayApiException {
        return  aliPayService.aliPay(new AlipayBean()
                 .setBody(body)
                 .setOut_trade_no(subject)
                 .setTotal_amount(new StringBuffer().append(total_amount))
                 .setSubject(subject));
    }

}

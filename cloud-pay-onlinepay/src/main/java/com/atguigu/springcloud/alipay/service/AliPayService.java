package com.atguigu.springcloud.alipay.service;

import com.alipay.api.AlipayApiException;
import com.atguigu.springcloud.alipay.entity.AlipayBean;

public interface AliPayService {
	 /*支付宝*/
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;
}

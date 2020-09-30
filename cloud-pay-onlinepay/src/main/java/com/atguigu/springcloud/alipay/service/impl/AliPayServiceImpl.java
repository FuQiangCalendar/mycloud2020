package com.atguigu.springcloud.alipay.service.impl;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.atguigu.springcloud.alipay.entity.AlipayBean;
import com.atguigu.springcloud.alipay.service.AliPayService;
import com.atguigu.springcloud.utils.AlipayUtil;

@Service
public class AliPayServiceImpl implements AliPayService {

	@Override
	public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
		return AlipayUtil.connect(alipayBean);
	}

}

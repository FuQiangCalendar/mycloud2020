package com.atguigu.springcloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Resource
	private PaymentDao paymentDao;
	
	@Override
	public int save(Payment payment) {
		return paymentDao.save(payment);
	}

	@Override
	public Payment getPaymentById(Long id) {
		return paymentDao.getPaymentById(id);
	}

}

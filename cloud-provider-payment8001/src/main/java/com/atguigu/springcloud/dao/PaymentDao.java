package com.atguigu.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.atguigu.springcloud.entities.Payment;

/**
 * 
 * @author MrFu
 *
 */
@Mapper
public interface PaymentDao {
	public int save(Payment payment);
	
	public Payment getPaymentById(@Param("id") Long id);
}

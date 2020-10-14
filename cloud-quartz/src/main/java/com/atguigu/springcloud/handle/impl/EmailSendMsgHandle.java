package com.atguigu.springcloud.handle.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.atguigu.springcloud.handle.ISendMsgHandle;
import com.atguigu.springcloud.utils.SpringContextUtils;

public class EmailSendMsgHandle implements ISendMsgHandle {
	static String emailFrom;
	public static void setEmailFrom(String emailFrom) {
		EmailSendMsgHandle.emailFrom = emailFrom;
	}

	@Override
	public void SendMsg(String es_receiver, String es_title, String es_content) {
		JavaMailSender mailSender = (JavaMailSender) SpringContextUtils.getBean("mailSender");
		SimpleMailMessage message = new SimpleMailMessage();
		// 设置发送方邮箱地址
		message.setFrom(emailFrom);
		message.setTo(es_receiver);
		message.setSubject(es_title);
		message.setText(es_content);
		mailSender.send(message);
	}
}

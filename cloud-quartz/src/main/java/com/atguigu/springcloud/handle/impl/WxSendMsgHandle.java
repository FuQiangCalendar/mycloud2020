package com.atguigu.springcloud.handle.impl;

import com.atguigu.springcloud.handle.ISendMsgHandle;

public class WxSendMsgHandle implements ISendMsgHandle {

	@Override
	public void SendMsg(String es_receiver, String es_title, String es_content) {
		// TODO Auto-generated method stub
		System.out.println("发微信消息模板");
	}

}

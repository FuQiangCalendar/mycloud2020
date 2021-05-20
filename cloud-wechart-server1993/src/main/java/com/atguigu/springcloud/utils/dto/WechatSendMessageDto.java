package com.atguigu.springcloud.utils.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @Package com.atguigu.springcloud.utils.dto
 * @ClassName WechatSendMessageDto
 * @Description 用户发送消息
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/8 16:31
 * @Version 1.0
 **/
@Data
@RequiredArgsConstructor
public class WechatSendMessageDto {

    private String toUser;

    private String fromUser;

    private String createTime;

    private String msgType;




}

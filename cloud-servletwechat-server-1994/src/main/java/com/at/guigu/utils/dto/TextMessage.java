package com.at.guigu.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
/**
* @Description : 文本消息类
* @Author:FuQiangCalendar
* @Date: 2021/5/6 10:43
*/
public class TextMessage {
    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
}
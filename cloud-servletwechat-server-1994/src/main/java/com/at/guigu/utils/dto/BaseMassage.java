package com.at.guigu.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
* @Description : 基本消息类
* @Author:FuQiangCalendar
* @Date: 2021/5/6 10:43
*/
public class BaseMassage{
    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    private String MsgType;
}
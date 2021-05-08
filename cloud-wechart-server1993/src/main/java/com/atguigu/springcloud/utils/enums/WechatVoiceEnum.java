package com.atguigu.springcloud.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
* @Description : 微信语音格式枚举类
* @Author:FuQiangCalendar
* @Date: 2021/5/8 8:35
*/
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum WechatVoiceEnum {
    AMR ("amr", "amr格式"),
    SPEEX ("speex", "speex格式"),
    ;
    private String code;

    private String msg;
}

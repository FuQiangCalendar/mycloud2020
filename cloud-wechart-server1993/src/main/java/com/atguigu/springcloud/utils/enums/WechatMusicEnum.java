package com.atguigu.springcloud.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* @Description :  微信音乐格式枚举类
* @Author:FuQiangCalendar
* @Date: 2021/5/8 15:25
 *
 * m4a, aac, mp3, wav
*/
@Getter
@AllArgsConstructor
public enum WechatMusicEnum {
    M4A ("m4a", "m4a音乐格式"),
    AAC ("aac", "aac音乐格式"),
    MP3 ("mp3", "mp3音乐格式"),
    WAV ("wav", "wav音乐格式"),
    ;
    private String code;

    private String msg;
}

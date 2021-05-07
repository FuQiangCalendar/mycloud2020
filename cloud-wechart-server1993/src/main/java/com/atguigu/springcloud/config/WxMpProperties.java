package com.atguigu.springcloud.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* @Description :微信接入验证
* @Author:FuQiangCalendar
* @Date: 2021/4/30 14:41
*/
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "wxmp")
public class WxMpProperties {

    /**
     * 设置微信公众号的appid
     */
    private String appId;
    /**
     * 设置微信公众号的app secret
     */
    private String appSecret;
    /**
     * 设置微信公众号的token
     */
    private String token;
    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;
}
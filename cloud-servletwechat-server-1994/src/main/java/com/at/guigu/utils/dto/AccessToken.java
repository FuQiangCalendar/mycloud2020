package com.at.guigu.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.shunchao.utils.dto
 * @ClassName AccessToken
 * @Description 获取微信的授权码
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/6 11:01
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccessToken {
    /**授权码*/
    private String accessToken;
    /**过期时间*/
    private Integer expiresIn;
}

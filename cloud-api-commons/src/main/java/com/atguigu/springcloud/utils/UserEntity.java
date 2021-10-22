package com.atguigu.springcloud.utils;

import com.atguigu.springcloud.utils.annotations.SensitiveWrapped;
import com.atguigu.springcloud.utils.enums.SensitiveEnum;
import lombok.Data;

@Data
public class UserEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号
     */
    @SensitiveWrapped(SensitiveEnum.MOBILE_PHONE)
    private String mobile;

    /**
     * 身份证号码
     */
    @SensitiveWrapped(SensitiveEnum.ID_CARD)
    private String idCard;

    /**
     * 年龄
     */
    private String sex;

    /**
     * 性别
     */
    private int age;

    //省略get、set...
}
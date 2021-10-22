package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.utils.UserEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FuQiangCalendar
 * @title: HelloController
 * @projectName cloud2020
 * @description: 脱敏测试
 * @date 2021/10/22 9:09
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping(value = "/sayHello")
    @ApiOperation(value = "打招呼", notes = "打招呼")
    public UserEntity hello() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1l);
        userEntity.setName("张三");
        userEntity.setMobile("18000000001");
        userEntity.setIdCard("420117200001011000008888");
        userEntity.setAge(20);
        userEntity.setSex("男");
        return userEntity;
    }
}

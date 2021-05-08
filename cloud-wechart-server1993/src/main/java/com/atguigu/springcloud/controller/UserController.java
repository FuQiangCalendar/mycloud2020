package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.WechatUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package com.atguigu.springcloud.controller
 * @ClassName UserController
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 16:30
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private WechatUserService userService;


}

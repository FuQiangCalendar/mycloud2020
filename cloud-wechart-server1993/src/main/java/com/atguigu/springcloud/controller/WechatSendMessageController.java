package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.WechatSendMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Package com.atguigu.springcloud.controller
 * @ClassName WechatSendMessageController
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/24 12:06
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/wxmp/sendMessage")
public class WechatSendMessageController {
    @Autowired
    private WechatSendMessageService wechatSendMessageService;

    @PostMapping("/sendTzsToCustomer")
    public void sendTzsToCustomer (MultipartFile multipartFile, String openId) {
        try {
            wechatSendMessageService.sendTzsToCustomer(multipartFile,openId);
            log.info("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("发送失败");
        }
    }
}

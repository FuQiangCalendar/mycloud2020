package com.at.guigu.controller;

import com.at.guigu.utils.CheckUtil;
import com.at.guigu.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Package com.at.guigu
 * @ClassName WechatController
 * @Description 微信公众号/服务号入口
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/6 14:09
 * @Version 1.0
 **/
@RestController
@Slf4j
public class WechatController {

    @GetMapping("/wx")
    public String entry(@RequestParam("signature") String signature,
                        @RequestParam("timestamp") String timestamp,
                        @RequestParam("nonce") String nonce,
                        @RequestParam("echostr") String echoStr) {
        log.info("微信公众号/服务号接入传递的参数 signature:[{}],timestamp:[{}],nonce:[{}],echostr:[{}]",
                signature, timestamp, nonce, echoStr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echoStr)) {
            log.error("接收到微信认证信息,参数非法,存在为空的参数");
            return "error";
        }

        boolean result = CheckUtil.checkSignature(signature,timestamp, nonce);
        log.info("微信公众号/服务号接入成功?[{}]", result);
        return result ? echoStr : "error";
    }

    @PostMapping("/wx")
    public void post (HttpServletRequest req, HttpServletResponse resp) {
        try{
            MessageUtil.doPost(req, resp);
        }catch (Exception e) {
            log.error("操作失败", e);
            e.printStackTrace();
        }
    }
}

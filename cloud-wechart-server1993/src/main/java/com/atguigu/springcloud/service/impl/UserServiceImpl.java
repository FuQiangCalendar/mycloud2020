package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpUserQuery;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package com.atguigu.springcloud.service.impl
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 10:44
 * @Version 1.0
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private WxMpService wxMpService;

    @Override
    public void getUserList() throws Exception{
        List<WxMpUser> wxMpUsers = wxMpService.getUserService().userInfoList(new WxMpUserQuery());
        log.info("wxMpUsers>>>>" + wxMpUsers);
    }
}

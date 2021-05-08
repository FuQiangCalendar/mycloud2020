package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.WechatUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
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
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private WxMpService wxMpService;

    @Override
    public List<WxMpUser> getUserList() throws Exception{
        WxMpUserList wxMpUserList = wxMpService.getUserService().userList(null);
        List<String> openids = wxMpUserList.getOpenids();
        //获取用户基本信息
        List<WxMpUser> wxMpUsers = wxMpService.getUserService().userInfoList(openids);
        return  wxMpUsers;
    }

    @Override
    public WxMpUser getUser(String openId) throws Exception {
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
        return wxMpUser;
    }

}

package com.atguigu.springcloud.service;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.List;

public interface WechatUserService {
    public List<WxMpUser> getUserList () throws Exception;

    /**
    * @Description :  获取指定用户的信息
    * @Param [openId] 关注用户的id
    * @return:me.chanjar.weixin.mp.bean.result.WxMpUser
    * @Author:FuQiangCalendar
    * @Date: 2021/5/7 16:43
    */
    WxMpUser getUser (String openId) throws Exception;

}

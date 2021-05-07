package com.atguigu.springcloud.handler.message;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.TEXT;

/**
 * @Package com.atguigu.springcloud.handler
 * @ClassName LinkMessageHandler
 * @Description 链接消息处理类
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 9:14
 * @Version 1.0
 *
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>  接收方微信号
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>  发送方微信号，若为普通用户，则是一个OpenID
 *   <CreateTime>1351776360</CreateTime>  消息创建时间
 *   <MsgType><![CDATA[link]]></MsgType>  消息类型，链接为link
 *   <Title><![CDATA[公众平台官网链接]]></Title>  消息标题
 *   <Description><![CDATA[公众平台官网链接]]></Description>  消息描述
 *   <Url><![CDATA[url]]></Url>  消息链接
 *   <MsgId>1234567890123456</MsgId>  消息id，64位整型
 * </xml>
 **/
@Component
@Slf4j
public class LinkMessageHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个消息", wxMessage.toString());
        // 1、获取微信用户信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        //2、获取链接位置消息的信息
        String toUser = wxMessage.getToUser();
        String fromUser = wxMessage.getFromUser();
        Long createTime = wxMessage.getCreateTime();
        String msgType = wxMessage.getMsgType();
        String title = wxMessage.getTitle();
        String description = wxMessage.getDescription();
        String url = wxMessage.getUrl();
        Long msgId = wxMessage.getMsgId();

        //链接消息发送成功的回复
        WxMpXmlOutTextMessage wxMpXmlOutTextMessage = new WxMpXmlOutTextMessage();
        wxMpXmlOutTextMessage.setContent("上传成功");
        wxMpXmlOutTextMessage.setCreateTime(new Date().getTime());
        wxMpXmlOutTextMessage.setFromUserName(toUser);
        wxMpXmlOutTextMessage.setMsgType(TEXT);
        wxMpXmlOutTextMessage.setToUserName(fromUser);
        return wxMpXmlOutTextMessage;
    }
}

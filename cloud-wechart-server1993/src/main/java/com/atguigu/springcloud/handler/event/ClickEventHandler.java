package com.atguigu.springcloud.handler.event;

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

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.TEXT;

/**
 * @Package com.atguigu.springcloud.handler.event
 * @ClassName ClickEventHandler
 * @Description 自定义菜单事件，点击事件回调
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 10:17
 * @Version 1.0
 *
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>  开发者微信号
 *   <FromUserName><![CDATA[FromUser]]></FromUserName> 发送方帐号（一个OpenID）
 *   <CreateTime>123456789</CreateTime>  消息创建时间 （整型）
 *   <MsgType><![CDATA[event]]></MsgType>  消息类型，event
 *   <Event><![CDATA[CLICK]]></Event>  事件类型，CLICK
 *   <EventKey><![CDATA[EVENTKEY]]></EventKey>  事件KEY值，与自定义菜单接口中KEY值对应
 * </xml>
 **/
@Component
@Slf4j
public class ClickEventHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个自定义菜单事件，点击事件回调", wxMessage.toString());
        // 1、获取微信用户信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        String toUser = wxMessage.getToUser();
        String fromUser = wxMessage.getFromUser();
        Long createTime = wxMessage.getCreateTime();
        String msgType = wxMessage.getMsgType();
        String event = wxMessage.getEvent();
        String eventKey = wxMessage.getEventKey();
        String url = wxMessage.getUrl();

        WxMpXmlOutTextMessage outMessage = new WxMpXmlOutTextMessage();
        outMessage.setContent("此功能正在开发中，敬请期待！");
        outMessage.setCreateTime(createTime);
        outMessage.setFromUserName(toUser);
        outMessage.setMsgType(TEXT);
        outMessage.setToUserName(fromUser);

        return outMessage;
    }
}

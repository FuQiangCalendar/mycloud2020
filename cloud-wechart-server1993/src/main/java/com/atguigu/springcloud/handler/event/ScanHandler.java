package com.atguigu.springcloud.handler.event;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Package com.atguigu.springcloud.handler.event
 * @ClassName ScanHandler
 * @Description 用户已关注时，扫描二维码的事件推送
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 10:06
 * @Version 1.0
 *
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName> 开发者微信号
 *   <FromUserName><![CDATA[FromUser]]></FromUserName>  发送方帐号（一个OpenID）
 *   <CreateTime>123456789</CreateTime>  消息创建时间 （整型）
 *   <MsgType><![CDATA[event]]></MsgType>  消息类型，event
 *   <Event><![CDATA[SCAN]]></Event>  事件类型，SCAN
 *   <EventKey><![CDATA[SCENE_VALUE]]></EventKey>  事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
 *   <Ticket><![CDATA[TICKET]]></Ticket>  二维码的ticket，可用来换取二维码图片
 * </xml>
 **/
@Component
@Slf4j
public class ScanHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个用户已关注时，扫描二维码的事件推送", wxMessage.toString());
        // 1、获取微信用户信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        String toUser = wxMessage.getToUser();
        String fromUser = wxMessage.getFromUser();
        Long createTime = wxMessage.getCreateTime();
        String msgType = wxMessage.getMsgType();
        String event = wxMessage.getEvent();
        String eventKey = wxMessage.getEventKey();
        String ticket = wxMessage.getTicket();

        return null;
    }
}

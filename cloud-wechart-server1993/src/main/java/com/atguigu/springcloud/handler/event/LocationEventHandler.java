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
 * @ClassName LocationEventHandler
 * @Description 上报地理位置事件，消息回调
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 10:11
 * @Version 1.0
 *
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>  开发者微信号
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>  发送方帐号（一个OpenID）
 *   <CreateTime>123456789</CreateTime>  消息创建时间 （整型）
 *   <MsgType><![CDATA[event]]></MsgType>  消息类型，event
 *   <Event><![CDATA[LOCATION]]></Event>  事件类型，LOCATION
 *   <Latitude>23.137466</Latitude>  地理位置纬度
 *   <Longitude>113.352425</Longitude>  地理位置经度
 *   <Precision>119.385040</Precision>  地理位置精度
 * </xml>
 **/

@Component
@Slf4j
public class LocationEventHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个上报地理位置事件，消息回调", wxMessage.toString());
        // 1、获取微信用户信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        String toUser = wxMessage.getToUser();
        String fromUser = wxMessage.getFromUser();
        Long createTime = wxMessage.getCreateTime();
        String msgType = wxMessage.getMsgType();
        String event = wxMessage.getEvent();
        Double latitude = wxMessage.getLatitude();
        Double longitude = wxMessage.getLongitude();
        Double precision = wxMessage.getPrecision();

        return null;
    }
}

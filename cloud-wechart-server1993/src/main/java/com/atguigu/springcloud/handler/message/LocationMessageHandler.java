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
 * @ClassName LocationMessageHandler
 * @Description 地理位置消息处理类
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 9:08
 * @Version 1.0
 *
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>    开发者微信号
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>  发送方帐号（一个OpenID）
 *   <CreateTime>1351776360</CreateTime>   消息创建时间 （整型）
 *   <MsgType><![CDATA[location]]></MsgType>   消息类型，地理位置为location
 *   <Location_X>23.134521</Location_X>   地理位置纬度
 *   <Location_Y>113.358803</Location_Y>  地理位置经度
 *   <Scale>20</Scale>    地图缩放大小
 *   <Label><![CDATA[位置信息]]></Label>  地理位置信息
 *   <MsgId>1234567890123456</MsgId>   消息id，64位整型
 * </xml>
 **/
@Component
@Slf4j
public class LocationMessageHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个消息", wxMessage.toString());
        // 1、获取微信用户信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        //2、获取地理位置消息的信息
        String toUser = wxMessage.getToUser();
        String fromUser = wxMessage.getFromUser();
        Long createTime = wxMessage.getCreateTime();
        String msgType = wxMessage.getMsgType();  //消息类型，地理位置为location
        Double locationX = wxMessage.getLocationX(); // 地理位置纬度
        Double locationY = wxMessage.getLocationY(); // 地理位置经度
        Double scale = wxMessage.getScale();  //地图缩放大小
        String label = wxMessage.getLabel();  //地理位置信息
        Long msgId = wxMessage.getMsgId();

        WxMpXmlOutTextMessage wxMpXmlOutTextMessage = new WxMpXmlOutTextMessage();
        wxMpXmlOutTextMessage.setContent("上传成功");
        wxMpXmlOutTextMessage.setCreateTime(new Date().getTime());
        wxMpXmlOutTextMessage.setFromUserName(toUser);
        wxMpXmlOutTextMessage.setMsgType(TEXT);
        wxMpXmlOutTextMessage.setToUserName(fromUser);
        return wxMpXmlOutTextMessage;
    }
}

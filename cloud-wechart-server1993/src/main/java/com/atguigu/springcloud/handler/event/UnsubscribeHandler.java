package com.atguigu.springcloud.handler.event;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description :处理取消关注--公众号回调消息
 * @Author:FuQiangCalendar
 * @Date: 2021/4/30 14:39
 *
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>  开发者微信号
 *   <FromUserName><![CDATA[FromUser]]></FromUserName>  发送方帐号（一个OpenID）
 *   <CreateTime>123456789</CreateTime>  消息创建时间 （整型）
 *   <MsgType><![CDATA[event]]></MsgType>  消息类型，event
 *   <Event><![CDATA[subscribe]]></Event>  事件类型，subscribe(订阅)、unsubscribe(取消订阅)
 *  </xml>
 */
@Component
@Slf4j
public class UnsubscribeHandler implements WxMpMessageHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个扫码取消订阅事件:[{}]", wxMessage.toString());
        return null;
    }
}
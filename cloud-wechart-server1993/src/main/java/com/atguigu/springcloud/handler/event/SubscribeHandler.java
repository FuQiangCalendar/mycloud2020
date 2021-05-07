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

import java.util.Date;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.TEXT;

/**
* @Description :处理关注公众号--回调消息
* @Author:FuQiangCalendar
* @Date: 2021/4/30 14:39
 *用户在关注与取消关注公众号时，微信会把这个事件推送到开发者填写的URL。方便开发者给用户下发欢迎消息或者做帐号的解绑。为保护用户数据隐私，开发者收到用户取消关注事件时需要删除该用户的所有信息。
 * 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次。
 * 关于重试的消息排重，推荐使用FromUserName + CreateTime 排重。
 * 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试。
 *
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>  开发者微信号
 *   <FromUserName><![CDATA[FromUser]]></FromUserName>  发送方帐号（一个OpenID）
 *   <CreateTime>123456789</CreateTime>  消息创建时间 （整型）
 *   <MsgType><![CDATA[event]]></MsgType>  消息类型，event
 *   <Event><![CDATA[subscribe]]></Event>  事件类型，subscribe(订阅)、unsubscribe(取消订阅)
 *   <EventKey><![CDATA[qrscene_123123]]></EventKey>  事件KEY值，qrscene_为前缀，后面为二维码的参数值
 *   <Ticket><![CDATA[TICKET]]></Ticket>  二维码的ticket，可用来换取二维码图片
 * </xml>
*/
@Component
@Slf4j
public class SubscribeHandler implements WxMpMessageHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个扫码订阅事件:[{}]", wxMessage.toString());
        // 1、获取微信用户信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        if (null == wxMpUser) {
            log.warn("从微信公众号获取用户(FromUser)信息:[{}]失败.", wxMessage.getFromUser());
            return null;
        }
        log.info("根据 openId:[{}]获取到的微信用户信息:[{}]", wxMessage.getFromUser(), wxMpUser.toString());
        String toUser = wxMessage.getToUser();
        String fromUser = wxMessage.getFromUser();
        Long createTime = wxMessage.getCreateTime();
        String msgType = wxMessage.getMsgType();
        String event = wxMessage.getEvent();
        String eventKey = wxMessage.getEventKey();
        String ticket = wxMessage.getTicket();

        WxMpXmlOutTextMessage outMessage = new WxMpXmlOutTextMessage();
        outMessage.setContent(menuText());
        outMessage.setCreateTime(new Date().getTime());
        outMessage.setFromUserName(wxMessage.getToUser());
        outMessage.setMsgType(TEXT);
        outMessage.setToUserName(wxMessage.getFromUser());
        return outMessage;
    }

    public static String menuText () {
        StringBuffer sb = new StringBuffer();
        sb.append("哈哈，撸代码的小强回來了，有事沒事來看下哦。\n");
        sb.append("沒事可以看我的博客：https://blog.csdn.net/qq_39337916\n");
        sb.append("欢迎回复文字。\n");
        sb.append("1:强哥真帅。\n");
        sb.append("2:强哥很帅。\n");
        sb.append("3:别说了。\n");

        return sb.toString();
    }
}
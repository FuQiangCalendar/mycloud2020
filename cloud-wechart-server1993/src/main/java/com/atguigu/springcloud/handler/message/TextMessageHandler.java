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

import java.util.Map;

/**
 * @Package com.atguigu.springcloud.handler
 * @ClassName TextMessageHandler
 * @Description 处理 TEXT 消息
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/6 16:11
 * @Version 1.0
 *格式
 * <xml>
 *  <ToUserName><![CDATA[toUser]]></ToUserName>
 *  <FromUserName><![CDATA[fromUser]]></FromUserName>
 *  <CreateTime>1348831860</CreateTime>
 *  <MsgType><![CDATA[text]]></MsgType>
 *  <Content><![CDATA[this is a test]]></Content>
 *  <MsgId>1234567890123456</MsgId>
 *  </xml>
 **/
@Component
@Slf4j
public class TextMessageHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个消息", wxMessage.toString());
        // 1、获取微信用户信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());

        String toUser = wxMessage.getToUser(); //接受人
        String fromUser = wxMessage.getFromUser(); //发送人
        Long createTime = wxMessage.getCreateTime(); //时间戳
        String msgType = wxMessage.getMsgType(); //消息类型
        String content = wxMessage.getContent(); //内容
        Long msgId = wxMessage.getMsgId();  //消息id

        WxMpXmlOutTextMessage outMessage = new WxMpXmlOutTextMessage();
        outMessage.setContent(generateContent(content));
        outMessage.setCreateTime(createTime);
        outMessage.setFromUserName(toUser);
        outMessage.setMsgType(msgType);
        outMessage.setToUserName(fromUser);
        log.info("发送给用户", wxMessage.getFromUser(), wxMpUser.toString());
        return outMessage;
    }

    public static String generateContent (String content) {
        String newContent = "哈哈哈";
        switch (content){
            case "1":
                newContent = "强哥真帅";
                break;
            case "2":
                newContent = "强哥很帅";
                break;
            case "3":
                newContent = "别说了";
                break;
            default:
                break;
        }
        return newContent;
    }
}

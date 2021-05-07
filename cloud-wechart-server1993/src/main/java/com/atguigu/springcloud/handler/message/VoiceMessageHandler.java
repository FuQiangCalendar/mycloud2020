package com.atguigu.springcloud.handler.message;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutVoiceMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Package com.atguigu.springcloud.handler
 * @ClassName VoiceMessageHandler
 * @Description 语音消息处理类
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 8:37
 * @Version 1.0
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>   开发者微信号
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>  发送方帐号（一个OpenID）
 *   <CreateTime>1357290913</CreateTime>   消息创建时间 （整型）
 *   <MsgType><![CDATA[voice]]></MsgType>   语音为voice
 *   <MediaId><![CDATA[media_id]]></MediaId>  语音消息媒体id，可以调用获取临时素材接口拉取该媒体
 *   <Format><![CDATA[Format]]></Format>   语音格式：amr
 *   <Recognition>< ![CDATA[腾讯微信团队] ]></Recognition>  语音识别结果，UTF8编码
 *   <MsgId>1234567890123456</MsgId>  消息id，64位整型
 * </xml>
 **/
@Component
@Slf4j
public class VoiceMessageHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个消息", wxMessage.toString());
        // 1、获取微信用户信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        //图片消息的信息
        String toUser = wxMessage.getToUser();  //接收人
        String fromUser = wxMessage.getFromUser();  //发送人
        Long createTime = wxMessage.getCreateTime();  //创建事件
        String msgType = wxMessage.getMsgType();  //消息类型
        String mediaId = wxMessage.getMediaId(); //媒体id
        Long msgId = wxMessage.getMsgId();  //消息id
        String format = wxMessage.getFormat();  //语音格式 arm

        /**请注意，开通语音识别后，用户每次发送语音给公众号时，微信会在推送的语音消息XML数据包中，
        增加一个Recognition字段（注：由于客户端缓存，开发者开启或者关闭语音识别功能，
        对新关注者立刻生效，对已关注用户需要24小时生效。开发者可以重新关注此帐号进行测试）*/
        String recognition = wxMessage.getRecognition();  //语音识别结果

        WxMpXmlOutVoiceMessage wxMpXmlOutVoiceMessage = new WxMpXmlOutVoiceMessage();
        wxMpXmlOutVoiceMessage.setMediaId(mediaId);
        wxMpXmlOutVoiceMessage.setCreateTime(new Date().getTime());
        wxMpXmlOutVoiceMessage.setFromUserName(toUser);
        wxMpXmlOutVoiceMessage.setMsgType(msgType);
        wxMpXmlOutVoiceMessage.setToUserName(fromUser);

        log.info("发送给用户", fromUser, wxMpUser.toString());
        return wxMpXmlOutVoiceMessage;
    }
}

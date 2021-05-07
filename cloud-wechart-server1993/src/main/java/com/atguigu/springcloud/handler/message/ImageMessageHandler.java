package com.atguigu.springcloud.handler.message;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.TEXT;

/**
 * @Package com.atguigu.springcloud.handler
 * @ClassName ImageMessageHandler
 * @Description 处理图片消息
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/6 17:13
 * @Version 1.0
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>1348831860</CreateTime>
 *   <MsgType><![CDATA[image]]></MsgType>
 *   <PicUrl><![CDATA[this is a url]]></PicUrl>
 *   <MediaId><![CDATA[media_id]]></MediaId>
 *   <MsgId>1234567890123456</MsgId>
 * </xml>
 **/
@Component
@Slf4j
public class ImageMessageHandler implements WxMpMessageHandler {
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
        String picUrl = wxMessage.getPicUrl();  //图片url
        String mediaId = wxMessage.getMediaId(); //媒体id
        Long msgId = wxMessage.getMsgId();  //消息id

        //回复图片消息的模板
        WxMpXmlOutImageMessage wxMpXmlOutImageMessage = new WxMpXmlOutImageMessage();
        wxMpXmlOutImageMessage.setCreateTime(new Date().getTime());
        wxMpXmlOutImageMessage.setMediaId(mediaId);
        wxMpXmlOutImageMessage.setFromUserName(toUser);
        wxMpXmlOutImageMessage.setMsgType(msgType);
        wxMpXmlOutImageMessage.setToUserName(fromUser);

        //图片长传成功的回复
        WxMpXmlOutTextMessage wxMpXmlOutTextMessage = new WxMpXmlOutTextMessage();
        wxMpXmlOutTextMessage.setContent("上传成功");
        wxMpXmlOutTextMessage.setCreateTime(new Date().getTime());
        wxMpXmlOutTextMessage.setFromUserName(toUser);
        wxMpXmlOutTextMessage.setMsgType(TEXT);
        wxMpXmlOutTextMessage.setToUserName(fromUser);
        log.info("发送给用户", fromUser, wxMpUser.toString());
        return wxMpXmlOutTextMessage;
    }
}

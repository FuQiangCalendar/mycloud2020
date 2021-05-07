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
 * @ClassName VideoMessageHandler
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 8:46
 * @Version 1.0
 * 视频
 *<xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>   开发者微信号
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>  发送方帐号（一个OpenID）
 *   <CreateTime>1357290913</CreateTime>   消息创建时间 （整型）
 *   <MsgType><![CDATA[video]]></MsgType>  视频为video
 *   <MediaId><![CDATA[media_id]]></MediaId>  视频消息媒体id，可以调用获取临时素材接口拉取数据。
 *   <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>  视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
 *   <MsgId>1234567890123456</MsgId> 消息id，64位整型
 * </xml>
 *
 * 小视频
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>1357290913</CreateTime>
 *   <MsgType><![CDATA[shortvideo]]></MsgType>  小视频为shortvideo
 *   <MediaId><![CDATA[media_id]]></MediaId>
 *   <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
 *   <MsgId>1234567890123456</MsgId>
 * </xml>
 **/
@Component
@Slf4j
public class VideoMessageHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("接收到一个消息", wxMessage.toString());
        // 1、获取微信用户信息
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
        //2、获取视频消息的信息
        String toUser = wxMessage.getToUser();
        String fromUser = wxMessage.getFromUser();
        Long createTime = wxMessage.getCreateTime();
        String msgType = wxMessage.getMsgType();
        String mediaId = wxMessage.getMediaId();
        String thumbMediaId = wxMessage.getThumbMediaId();
        Long msgId = wxMessage.getMsgId();

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

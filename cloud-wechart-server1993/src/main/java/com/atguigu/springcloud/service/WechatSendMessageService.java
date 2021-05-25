package com.atguigu.springcloud.service;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Package com.atguigu.springcloud.service
 * @ClassName WechatSendMessageService
 * @Description 公众号发消息业务层
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 16:40
 * @Version 1.0
 **/
public interface WechatSendMessageService {

    /**
    * @Description : 向关注公众号的用户群发文本消息
    * @Param [content] 文本内容
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/7 16:47
     *
     * 文本内容模板如下
     * <xml>
     *   <ToUserName><![CDATA[toUser]]></ToUserName>  接收方帐号（收到的OpenID）
     *   <FromUserName><![CDATA[fromUser]]></FromUserName>  开发者微信号
     *   <CreateTime>12345678</CreateTime>  消息创建时间 （整型）
     *   <MsgType><![CDATA[text]]></MsgType>  消息类型，文本为text
     *   <Content><![CDATA[你好]]></Content>  回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
     * </xml>
    */
    public void groupPostTextMessage (String content) throws Exception;

    /**
    * @Description : 向关注公众号的某个用户发文本消息
    * @Param [content, openId]
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/7 16:59
    */
    public void singlePostTextMeassge (String content, String openId) throws Exception;

    /**
    * @Description : 向关注公众号的用户群发单个文件
    * @Param [multipartFile]
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/7 17:37
     *
     * <xml>
     *   <ToUserName><![CDATA[toUser]]></ToUserName>  接收方帐号（收到的OpenID）
     *   <FromUserName><![CDATA[fromUser]]></FromUserName>  开发者微信号
     *   <CreateTime>12345678</CreateTime>  消息创建时间 （整型）
     *   <MsgType><![CDATA[image]]></MsgType>  消息类型，图片为image
     *   <Image>
     *     <MediaId><![CDATA[media_id]]></MediaId>  通过素材管理中的接口上传多媒体文件，得到的id。
     *   </Image>
     * </xml>
    */
    public void groupPostImageMessage (MultipartFile multipartFile) throws Exception;

    /**
    * @Description : 向关注公众号的某个用户发单个文件
    * @Param [multipartFile, openId]
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/7 17:52
    */
    public void singlePostImageMessage (MultipartFile multipartFile, String openId) throws Exception;

    /**
    * @Description : 向关注公众号的用户群发语音消息
    * @Param [multipartFile]
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/8 8:41
     *
     * <xml>
     *   <ToUserName><![CDATA[toUser]]></ToUserName>
     *   <FromUserName><![CDATA[fromUser]]></FromUserName>
     *   <CreateTime>12345678</CreateTime>
     *   <MsgType><![CDATA[voice]]></MsgType>
     *   <Voice>
     *     <MediaId><![CDATA[media_id]]></MediaId>
     *   </Voice>
     * </xml>
    */
    public void groupPostVoiceMessage (MultipartFile multipartFile) throws Exception;

    /**
    * @Description : 向关注公众号的某个用户发单个语音消息
    * @Param [multipartFile, openId]
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/8 8:43
    */
    public void singlePostVoiceMessage (MultipartFile multipartFile, String openId) throws Exception;

    /**
    * @Description : 向关注公众号的用户群发视频消息
    * @Param [multipartFile]
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/8 15:47
     *
     * <xml>
     *   <ToUserName><![CDATA[toUser]]></ToUserName>  接收方帐号（收到的OpenID）
     *   <FromUserName><![CDATA[fromUser]]></FromUserName>   开发者微信号
     *   <CreateTime>12345678</CreateTime>  消息创建时间 （整型）
     *   <MsgType><![CDATA[video]]></MsgType>  消息类型，视频为video
     *   <Video>
     *     <MediaId><![CDATA[media_id]]></MediaId>  通过素材管理中的接口上传多媒体文件，得到的id
     *     <Title><![CDATA[title]]></Title>  视频消息的标题
     *     <Description><![CDATA[description]]></Description>  视频消息的描述
     *   </Video>
     * </xml>
    */
    public void groupPostVideoMessage (MultipartFile multipartFile) throws Exception;

    /**
    * @Description : 向关注公众号的某个用户发单个视频消息
    * @Param [multipartFile, openId]
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/8 15:49
    */
    public void singlePostVideoMessage (MultipartFile multipartFile, String openId) throws Exception;

    /**
    * @Description : 上传文件
    * @Param [multipartFile]
    * @return:me.chanjar.weixin.common.bean.result.WxMediaUploadResult
    * @Author:FuQiangCalendar
    * @Date: 2021/5/8 8:49
    */
    public WxMediaUploadResult uploadMedia (MultipartFile multipartFile) throws Exception;

    public void sendTzsToCustomer (MultipartFile multipartFile, String openId) throws Exception;
}

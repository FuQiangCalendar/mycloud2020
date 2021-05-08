package com.atguigu.springcloud.config;

import com.atguigu.springcloud.handler.event.ClickEventHandler;
import com.atguigu.springcloud.handler.message.*;
import com.atguigu.springcloud.handler.event.SubscribeHandler;
import com.atguigu.springcloud.handler.event.UnsubscribeHandler;
import me.chanjar.weixin.common.api.WxMessageInMemoryDuplicateChecker;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType.*;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.*;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.LOCATION;

/**
 * @Description :处理公众号回调消息
 * @Author:FuQiangCalendar
 * @Date: 2021/4/30 14:39
 */
@Configuration
public class WxMpConfiguration {

    @Autowired
    private WxMpProperties wxMpProperties;
    @Autowired
    private SubscribeHandler subscribeHandler;
    @Autowired
    private UnsubscribeHandler unsubscribeHandler;
    @Autowired
    private TextMessageHandler textMessageHandler;
    @Autowired
    private ImageMessageHandler imageMessageHandler;
    @Autowired
    private VoiceMessageHandler voiceMessageHandler;
    @Autowired
    private VideoMessageHandler videoMessageHandler;
    @Autowired
    private LocationMessageHandler locationMessageHandler;
    @Autowired
    private LinkMessageHandler linkMessageHandler;
    @Autowired
    private ClickEventHandler clickEventHandler;

    @Bean
    public WxMpService wxMpService() {
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        // 设置多个微信公众号的配置
        // wxMpService.setMultiConfigStorages();
        return wxMpService;
    }

    /**
     * 这个地方的配置是保存在本地，生产环境需要自己扩展，可以保存在Redis中等等
     *
     * @return WxMpConfigStorage
     */
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl storage = new WxMpDefaultConfigImpl();
        storage.setAppId(wxMpProperties.getAppId());
        storage.setSecret(wxMpProperties.getAppSecret());
        storage.setAesKey(wxMpProperties.getAesKey());
        storage.setToken(wxMpProperties.getToken());
        return storage;
    }

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 消息去重
        router.setMessageDuplicateChecker(new WxMessageInMemoryDuplicateChecker());

        // 关注事件
        router.rule().async(false).msgType(EVENT).event(SUBSCRIBE)
                .handler(this.subscribeHandler)
                .end();
        // 取消关注事件
        router.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE)
                .handler(this.unsubscribeHandler)
                .end();
        // 菜单点击事件
        /*router.rule().async(false).msgType(EVENT).event(CLICK)
                .handler(this.clickEventHandler)
                .end();*/

        //处理文本消息
        router.rule().async(false).msgType(TEXT).handler(this.textMessageHandler).end();
        //处理图片消息
        router.rule().async(false).msgType(IMAGE).handler(this.imageMessageHandler).end();
        //处理语音消息
        router.rule().async(false).msgType(VOICE).handler(this.voiceMessageHandler).end();
        //处理视频消息
        router.rule().async(false).msgType(VIDEO).handler(this.videoMessageHandler).end();
        //处理地理位置消息
        router.rule().async(false).msgType(LOCATION).handler(this.locationMessageHandler).end();
        //处理链接消息
        router.rule().async(false).msgType(LINK).handler(this.linkMessageHandler).end();
        return router;
    }
}
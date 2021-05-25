package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.WechatSendMessageService;
import com.atguigu.springcloud.service.WechatUserService;
import com.atguigu.springcloud.utils.enums.WechatImageEnum;
import com.atguigu.springcloud.utils.enums.WechatMusicEnum;
import com.atguigu.springcloud.utils.enums.WechatVideoEnum;
import com.atguigu.springcloud.utils.enums.WechatVoiceEnum;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Package com.atguigu.springcloud.service.impl
 * @ClassName SendMessageServiceImpl
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 16:48
 * @Version 1.0
 **/
@Service
@Slf4j
public class WechatSendMessageServiceImpl implements WechatSendMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatUserService wechatUserService;

    @Override
    public void groupPostTextMessage(String content) throws Exception {
        List<WxMpUser> userList = wechatUserService.getUserList();
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setMsgType(WxConsts.KefuMsgType.TEXT);
        wxMpKefuMessage.setContent(content);

        for (WxMpUser user : userList) {
            wxMpKefuMessage.setToUser(user.getOpenId());
            boolean b = wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
            log.info(b ? "发送成功！" : "发送失败！");
        }
    }

    @Override
    public void singlePostTextMeassge(String content, String openId) throws Exception {
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setMsgType(WxConsts.KefuMsgType.TEXT);
        wxMpKefuMessage.setContent(content);
        wxMpKefuMessage.setToUser(openId);
        boolean b = wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        log.info(b ? "发送成功！" : "发送失败！");
    }

    @Override
    public void groupPostImageMessage(MultipartFile multipartFile) throws Exception {
        WxMediaUploadResult wxMediaUploadResult = this.uploadMedia(multipartFile);

    }

    @Override
    public void singlePostImageMessage(MultipartFile multipartFile, String openId) throws Exception {

    }

    @Override
    public void groupPostVoiceMessage(MultipartFile multipartFile) throws Exception {

    }

    @Override
    public void singlePostVoiceMessage(MultipartFile multipartFile, String openId) throws Exception {

    }

    @Override
    public void groupPostVideoMessage(MultipartFile multipartFile) throws Exception {

    }

    @Override
    public void singlePostVideoMessage(MultipartFile multipartFile, String openId) throws Exception {

    }


    @Override
    public WxMediaUploadResult uploadMedia(MultipartFile multipartFile) throws Exception {
        InputStream inputStream = multipartFile.getInputStream();
        String originalFilename = multipartFile.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        //语音格式
        List<WechatVoiceEnum> wechatVoiceEnums = new ArrayList<>(Arrays.asList(WechatVoiceEnum.values()));
        //图片格式
        List<WechatImageEnum> wechatImageEnums = new ArrayList<>(Arrays.asList(WechatImageEnum.values()));
        //视频格式
        List<WechatVideoEnum> wechatVideoEnums = new ArrayList<>(Arrays.asList(WechatVideoEnum.values()));

        //音乐格式
        List<WechatMusicEnum> wechatMusicEnums = new ArrayList<>(Arrays.asList(WechatMusicEnum.values()));

        WxMediaUploadResult wxMediaUploadResult = null;
        if (wechatVoiceEnums.stream().anyMatch(e -> StringUtils.equals(e.getCode(),ext))) {
            //文件为语音格式
            wxMediaUploadResult = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.VOICE, ext, inputStream);
        }else if (wechatImageEnums.stream().anyMatch(e -> StringUtils.equals(e.getCode(),ext))) {
            //文件为图片格式
            wxMediaUploadResult = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, ext, inputStream);
        }else if (wechatVideoEnums.stream().anyMatch(e -> StringUtils.equals(e.getCode(), ext))){
            //文件为视频格式
            wxMediaUploadResult = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.VIDEO, ext, inputStream);
        }else {
            //文件格式
            wxMediaUploadResult = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.FILE, ext, inputStream);
        }
        return wxMediaUploadResult;
    }

    @Override
    public void sendTzsToCustomer(MultipartFile multipartFile, String openId) throws Exception {
        WxMediaUploadResult wxMediaUploadResult = this.uploadMedia(multipartFile);
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setMsgType(WxConsts.KefuMsgType.FILE);
        wxMpKefuMessage.setMediaId(wxMediaUploadResult.getMediaId());
        wxMpKefuMessage.setToUser(openId);
        boolean b = wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        log.info(b ? "发送成功！" : "发送失败！");
    }


}

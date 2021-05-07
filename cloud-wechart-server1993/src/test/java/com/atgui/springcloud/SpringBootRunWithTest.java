package com.atgui.springcloud;

import com.atguigu.springcloud.WechatApplication;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.atgui.springcloud
 * @ClassName SpringBootRunWithTest
 * @Description 启动测试类
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 10:26
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WechatApplication.class)
@Slf4j
public class SpringBootRunWithTest {

    @Autowired
    private WxMpService wxMpService;

    /**
    * @Description : 基础支持
    * @Param []
    * @return:void
    * @Author:FuQiangCalendar
    * @Date: 2021/5/7 11:13
    */
    @Test
    public void test1 () throws Exception {
        //1、获取AccessToken
        String accessToken = wxMpService.getAccessToken();
        log.info("accessToken>>>" + accessToken);
        //2、多媒体文件上传接口
        WxMediaUploadResult image = wxMpService.getMaterialService().mediaUpload("image", new File("11"));
        //3、下载多媒体文件接口
        File file = wxMpService.getMaterialService().mediaDownload("123");
        //4、上传logo接口
        WxMediaImgUploadResult wxMediaImgUploadResult = wxMpService.getMaterialService().mediaImgUpload(new File("123"));

    }

    /**
    * @Description : 用户管理
    * @Param
    * @return:
    * @Author:FuQiangCalendar
    * @Date: 2021/5/7 10:56
    */
    @Test
    public void test2 () throws Exception {
        String accessToken = wxMpService.getAccessToken();
        log.info("accessToken>>>" + accessToken);

        //获取关注这列表
        WxMpUserList wxMpUserList = wxMpService.getUserService().userList(null);
        List<String> openids = wxMpUserList.getOpenids();
        //获取用户基本信息
        List<WxMpUser> wxMpUsers = wxMpService.getUserService().userInfoList(openids);
        log.info("wxMpUsers>>>>" + wxMpUsers);
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMpUserList.getNextOpenid());
        log.info("wxMpUser>>>>" + wxMpUser);
    }

    /**
    * 向用户发消息
    */
    @Test
    public void test3 () throws Exception{
        boolean b = wxMpService.getKefuService().sendKefuMessage(new WxMpKefuMessage());
    }

    /**
     * 自定义菜单
     */
    @Test
    public void test4 () throws Exception {
        //1、创建
//        String s = wxMpService.getMenuService().menuCreate("");
        WxMenu wxMenu = new WxMenu();
        WxMenuButton wxMenuButton = new WxMenuButton();
        wxMenuButton.setType("click");
        wxMenuButton.setName("获取官文");
        wxMenuButton.setKey("tzs");
        wxMenuButton.setUrl("https://www.baidu.com/");
        wxMenu.setButtons(new ArrayList<WxMenuButton>() {{
            add(wxMenuButton);
        }});
        String s1 = wxMpService.getMenuService().menuCreate(wxMenu);
        log.info("s1>>" + s1);
        //2、获取
        WxMpMenu wxMpMenu = wxMpService.getMenuService().menuGet();
        log.info("wxMpMenu>>" + wxMpMenu);
        //3、删除
//        wxMpService.getMenuService().menuDelete("1");
    }

    /**
    * 推广支持
    */
    @Test
    public void test5() throws Exception {
        //换取永久字符串二维码ticket
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateLastTicket("");
        WxMpQrCodeTicket wxMpQrCodeTicket1 = wxMpService.getQrcodeService().qrCodeCreateLastTicket(1);
        //换取临时二维码ticket
        WxMpQrCodeTicket wxMpQrCodeTicket2 = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 1);
        WxMpQrCodeTicket wxMpQrCodeTicket3 = wxMpService.getQrcodeService().qrCodeCreateTmpTicket("1", 1);
        
        //换取二维码图片url地址
        String s = wxMpService.getQrcodeService().qrCodePictureUrl("1");

        //换取二维码图片文件，jpg格式
        File file = wxMpService.getQrcodeService().qrCodePicture(new WxMpQrCodeTicket());
    }


}

package com.atgui.springcloud;

import com.atguigu.springcloud.WechatApplication;
import com.atguigu.springcloud.config.WxMpProperties;
import com.atguigu.springcloud.service.impl.WechatUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
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
 * @Description 微信公众号单元测试  使用Mockito 测试框架
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/7 10:26
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WechatApplication.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WechatUserServiceImpl.class)
@Slf4j
public class SpringBootRunWithTest {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpProperties wxMpProperties;
    @Autowired
    private WechatUserServiceImpl wechatUserServiceImpl;
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

        WxMpUserList wxMpUserList = wxMpService.getUserService().userList(null);
        List<String> openids = wxMpUserList.getOpenids();
        //获取用户基本信息
        List<WxMpUser> wxMpUsers = wxMpService.getUserService().userInfoList(openids);
        log.info("用户信息:{}", wxMpUsers);

        //2、多媒体文件上传接口
        /*WxMediaUploadResult image = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, new File("F:/动漫基地/04.jpg"));
        //3、下载多媒体文件接口
        File file = wxMpService.getMaterialService().mediaDownload(image.getMediaId());
        File saveFile = new File("C:/Users/Administrator/Desktop/测试/wechat" + File.separator + file.getName());
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }

        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }

        FileUtils.copyFile(file, saveFile);*/
       /* WxMpMaterialFileBatchGetResult wxMpMaterialFileBatchGetResult = wxMpService.getMaterialService().materialFileBatchGet(WxConsts.MediaFileType.IMAGE, 0, 20);
        log.info(new GsonJsonProvider().toJson(wxMpMaterialFileBatchGetResult));

        WxMpMaterialFileBatchGetResult wxMpMaterialFileBatchGetResult1 = wxMpService.getMaterialService().materialFileBatchGet(WxConsts.MaterialType.IMAGE, 0, 20);
        log.info(new GsonJsonProvider().toJson(wxMpMaterialFileBatchGetResult1));

        WxMpMaterialFileBatchGetResult wxMpMaterialFileBatchGetResult2 = wxMpService.getMaterialService().materialFileBatchGet(WxConsts.MaterialType.NEWS, 0, 20);
        log.info(new GsonJsonProvider().toJson(wxMpMaterialFileBatchGetResult2));*/
        //4、上传logo接口
//        WxMediaImgUploadResult wxMediaImgUploadResult = wxMpService.getMaterialService().mediaImgUpload(new File("F:/动漫基地/04.jpg"));

    }

    /**
    * 上传logo
    */
    @Test
    public void uploadLogo () throws Exception{
        WxMediaImgUploadResult wxMediaImgUploadResult = wxMpService.getMaterialService().mediaImgUpload(new File("F:/动漫基地/04.jpg"));
        String url = wxMediaImgUploadResult.getUrl();
        log.info("logoURL:" + url);
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

        //获取关注列表
        WxMpUserList wxMpUserList = wxMpService.getUserService().userList(null);
        List<String> openids = wxMpUserList.getOpenids();
        //获取用户基本信息
        List<WxMpUser> wxMpUsers = wxMpService.getUserService().userInfoList(openids);
        log.info("wxMpUsers>>>>" + wxMpUsers);
        WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMpUserList.getNextOpenid());
        log.info("wxMpUser>>>>" + wxMpUser);

//        List<WxMpChangeOpenid> wxMpChangeOpenids = wxMpService.getUserService().changeOpenid("1", openids);

        List<WxUserTag> wxUserTags = wxMpService.getUserTagService().tagGet();
        log.info("wxUserTags>>>>" + wxUserTags);
        //授权
//        wxMpService.getOAuth2Service().getAccessToken();
        //获取公众号信息
        WxMpConfigStorage wxMpConfigStorage = wxMpService.getWxMpConfigStorage();
        String oauth2redirectUri = wxMpConfigStorage.getOauth2redirectUri();
        log.info("wxMpConfigStorage>>>" + wxMpConfigStorage);
        log.info("oauth2redirectUri>>>" + oauth2redirectUri);

//        wxMpService.getOAuth2Service().getUserInfo();

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
        wxMenuButton.setType(WxConsts.EventType.VIEW);
        wxMenuButton.setName("渡鸥官网");
        wxMenuButton.setKey("DUOU");
        wxMenuButton.setUrl("http://duou.com/");

        WxMenuButton wxMenuButton2 = new WxMenuButton();
        wxMenuButton2.setType(WxConsts.EventType.VIEW);
        wxMenuButton2.setName("绑定");
        wxMenuButton2.setKey("BANGDING");
        wxMenuButton2.setUrl("http://www.duou.com/wx/login.html");

        wxMenu.setButtons(new ArrayList<WxMenuButton>() {{
            add(wxMenuButton);
            add(wxMenuButton2);
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
    * 生成多成菜单
    */
    @Test
    public void generateMenu () {
        WxMenu wxMenu = new WxMenu();
        WxMenuButton wxMenuButton = new WxMenuButton();
        wxMenuButton.setType("click");
        wxMenuButton.setName("今日歌曲");
        wxMenuButton.setKey("V1001_TODAY_MUSIC");

        WxMenuButton wxMenuButton1 = new WxMenuButton();
        wxMenuButton1.setName("菜单");
        //子button
        WxMenuButton wxMenuButton1_1 = new WxMenuButton();
        wxMenuButton1_1.setName("搜索");
        wxMenuButton1_1.setType("view");
        wxMenuButton1_1.setUrl("http://www.baidu.com/");

        WxMenuButton wxMenuButton1_2 = new WxMenuButton();
        wxMenuButton1_2.setType("miniprogram");
        wxMenuButton1_2.setName("wxa");
        wxMenuButton1_2.setUrl("http://mp.weixin.qq.com");
        wxMenuButton1_2.setAppId("wxf88734ba1fc9fcc0");
        wxMenuButton1_2.setPagePath("pages/lunar/index");

        WxMenuButton wxMenuButton1_3 = new WxMenuButton();
        wxMenuButton1_3.setType("click");
        wxMenuButton1_3.setName("赞一下我们");
        wxMenuButton1_3.setKey("V1001_GOOD");

        wxMenuButton1.setSubButtons(new ArrayList<WxMenuButton>(){{
            add(wxMenuButton1_1);
//            add(wxMenuButton1_2);
            add(wxMenuButton1_3);
        }});

        wxMenu.setButtons(new ArrayList<WxMenuButton>() {{
            add(wxMenuButton);
            add(wxMenuButton1);
        }});
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

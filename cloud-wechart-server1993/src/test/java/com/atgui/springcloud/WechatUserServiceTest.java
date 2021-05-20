package com.atgui.springcloud;

import com.atguigu.springcloud.service.impl.WechatUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

/**
 * @Package com.atgui.springcloud
 * @ClassName WechatUserServiceTest
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/12 10:13
 * @Version 1.0
 **/
@Slf4j
public class WechatUserServiceTest extends MockitoTest2{
    @MockBean
    private WxMpService wxMpService;
    @Test
    public void test1 () throws Exception{
        WechatUserServiceImpl wechatUserService = new WechatUserServiceImpl();
        List<WxMpUser> wxMpUsers = wechatUserService.getUserList();
        log.info("wxMpUsers : {}", wxMpUsers);
    }
}

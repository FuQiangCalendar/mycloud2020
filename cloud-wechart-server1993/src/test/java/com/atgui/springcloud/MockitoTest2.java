package com.atgui.springcloud;

import com.atguigu.springcloud.service.WechatUserService;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @Package com.atgui.springcloud
 * @ClassName MockitoTest2
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/12 10:08
 * @Version 1.0
 **/
@RunWith(MockitoJUnitRunner.class)  // 可以自动触发所有的@Mock注解对象的创建
public class MockitoTest2 {
    @Mock
    private WechatUserService wechatUserService;
}

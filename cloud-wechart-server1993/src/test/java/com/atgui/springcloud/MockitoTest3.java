package com.atgui.springcloud;

import com.atguigu.springcloud.service.WechatUserService;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * @Package com.atgui.springcloud
 * @ClassName MockitoTest3
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/12 10:09
 * @Version 1.0
 **/
public class MockitoTest3 {
    @Mock
    private WechatUserService wechatUserService;

    @Rule  // 可以自动触发所有的@Mock注解对象的创建
    public MockitoRule mockitoRule = MockitoJUnit.rule();
}

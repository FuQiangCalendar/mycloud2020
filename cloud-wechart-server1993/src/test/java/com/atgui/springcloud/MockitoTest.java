package com.atgui.springcloud;

import com.atguigu.springcloud.service.WechatUserService;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @Package com.atgui.springcloud
 * @ClassName MockitoTest
 * @Description TODO
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/12 10:05
 * @Version 1.0
 **/
public class MockitoTest {
    @Mock
    private WechatUserService wechatUserService;

    /**
    * 注意的一点是，如果使用@Mock注解方法，你必须要触发@Mock注解对象的创建。
     * 使用MockitoAnnotations.initMocks(this)方法触发所有的@Mock注解对象的创建
    */
    @Before // @Test注解的方法运行前执行，触发所有的@Mock注解对象的创建
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}

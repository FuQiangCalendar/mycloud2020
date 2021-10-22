package com.atguigu.springcloud;

import com.atguigu.springcloud.utils.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author FuQiangCalendar
 * @title: MyTest
 * @projectName cloud2020
 * @description: TODO
 * @date 2021/10/22 9:02
 */
@Slf4j
public class MyTest {

    @Test
    public void test1 () throws JsonProcessingException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1l);
        userEntity.setName("张三");
        userEntity.setMobile("18000000001");
        userEntity.setIdCard("420117200001011000008888");
        userEntity.setAge(20);
        userEntity.setSex("男");

        //通过jackson方式，将对象序列化成json字符串
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(userEntity));
    }
}

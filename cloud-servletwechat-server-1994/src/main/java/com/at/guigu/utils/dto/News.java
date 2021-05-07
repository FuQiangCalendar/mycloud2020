package com.at.guigu.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
/**
* @Description : 新闻类
* @Author:FuQiangCalendar
* @Date: 2021/5/6 10:44
*/
public class News{
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;
}
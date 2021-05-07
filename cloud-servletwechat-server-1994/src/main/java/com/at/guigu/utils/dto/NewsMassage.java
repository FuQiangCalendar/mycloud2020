package com.at.guigu.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
* @Description :  新闻消息列表
* @Author:FuQiangCalendar
* @Date: 2021/5/6 10:45
*/
public class NewsMassage extends BaseMassage{
    private int ArticleCount;
    private List<News> Articles;
}
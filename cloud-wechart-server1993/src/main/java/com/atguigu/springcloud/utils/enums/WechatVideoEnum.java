package com.atguigu.springcloud.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Package com.atguigu.springcloud.utils.enums
 * @ClassName WechatVideoEnum
 * @Description 微信视频格式
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/8 14:49
 * @Version 1.0
 *
 * avi、wmv、mpeg、mp4、mov、mkv、flv、f4v、m4v、rmvb、rm、3gp、dat、ts、mts、vob、fly
 **/
@Getter
@AllArgsConstructor
public enum WechatVideoEnum {

    AVI ("avi", "avi视频格式"),
    WMV ("wmv", "wmv视频格式"),
    MPEG ("mpeg", "mpeg视频格式"),
    MP4 ("mp4", "mp4视频格式"),
    MOV ("mov", "mov视频格式"),
    MKV ("mkv", "mkv视频格式"),
    FLV ("flv", "flv视频格式"),
    F4V ("f4v", "f4v视频格式"),
    M4V ("m4v", "m4v视频格式"),
    RMVB ("rmvb", "rmvb视频格式"),
    RM ("rm", "rm视频格式"),
    GP_3 ("3gp", "3gp视频格式"),
    DAT ("dat", "dat视频格式"),
    TS ("ts", "ts视频格式"),
    MTS ("mts", "mts视频格式"),
    VOB ("vob", "vob视频格式"),
    FLY ("fly", "fly视频格式"),
    ;
    private String code;

    private String msg;

}

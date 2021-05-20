package com.atguigu.springcloud.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Package com.atguigu.springcloud.utils.enums
 * @ClassName WechatImageEnum
 * @Description 微信图片格式汇总
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/8 9:01
 * @Version 1.0
 *
 * bmp,jpg,png,tif,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,WMF,webp,avif等
 **/
@Getter
@AllArgsConstructor
public enum WechatImageEnum {
    BMP ("bmp", "bmp格式图片"),
    JPG ("jpg", "jpg格式图片"),
    PNG ("png", "png格式图片"),
    TIF ("tif", "tif格式图片"),
    GIF ("gif", "gif格斯图片"),
    PCX ("pcx", "pcx格式图片"),
    TGA ("tga", "tga格式图片"),
    EXIF ("exif", "exif格式图片"),
    FPX ("fpx", "fpx格式图片"),
    SVG ("svg", "svg格式图片"),
    PSD ("psd", "psd格式图片"),
    CDR ("cdr", "cdr格式图片"),
    PCD ("pcd", "pcd格式图片"),
    DXF ("dxf", "dxf格式图片"),
    UFO ("ufo", "ufo格式图片"),
    EPS ("eps", "eps格式图片"),
    AI ("ai", "ai格式图片"),
    RAW ("raw", "raw格式图片"),
    WMF ("WMF", "WMF格式图片"),
    WEBP ("webp", "webp格式图片"),
    AVIF ("avif", "avif格式图片"),
    ;
    private String code;

    private String msg;
}

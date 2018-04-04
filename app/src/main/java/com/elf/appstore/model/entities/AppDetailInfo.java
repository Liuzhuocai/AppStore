package com.elf.appstore.model.entities;

import java.util.List;

/**
 * Created by antino on 18-3-22.
 * 应用详情页面数据,服务器获得
 */

public class AppDetailInfo {
    //TODO:antino 根据服务器设计得到具体的应用详情信息
    private String appIcon;
    private String bigAppIcon;
    private int appId;
    private String packageName;//应用报名
    private String appName; //应用名
    private int applevel;   //评级
    private int downloads; //下载次数
    private String downloadCountStr;
    private String downloadUrl;  //下载地址
    private long appSize;        //应用大小
    private String versionName; //版本名
    private int versionCode;    //版本号
    private String md5;
    private String updateTime;
    private String author;   //作者
    private String changeLog;//版本修改内容
    private String getMemo; //应用简要说明（短）
    private String appDescription; //应用说明（长）
    private String surportLanguage;//语言支持
    private String appType;
    private List<AppImageInfo> appImageList;//图片展示
    public static class AppImageInfo {
        private String type;
        private String normalPic;
        private String smallPic;

        public String getNormalPic() {
            return normalPic;
        }

        public void setNormalPic(String normalPic) {
            this.normalPic = normalPic;
        }

        public String getSmallPic() {
            return smallPic;
        }

        public void setSmallPic(String smallPic) {
            this.smallPic = smallPic;
        }

        @Override
        public String toString() {
            return "AppImageInfo [normalPic=" + normalPic + ", smallPic="
                    + smallPic + "]";
        }
    }
}

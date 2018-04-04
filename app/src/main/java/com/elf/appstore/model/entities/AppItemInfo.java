package com.elf.appstore.model.entities;

/**
 * 页面列表中应用数据,服务器获得
 * Created by antino on 18-3-22.
 */

public class AppItemInfo {
    //TODO：antino 应用的服务器信息
    private int appId;
    private String appIcon;
    private String bigAppIcon;
    private String appName;
    private String packageName;
    private int versionCode;
    private String versionName;
    private int applevel;
    private long downloads;
    private String downloadCountStr;
    private long appSize;
    private String appType;
    private String md5;
    private String downloadUrl;
    private String appMemo;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getBigAppIcon() {
        return bigAppIcon;
    }

    public void setBigAppIcon(String bigAppIcon) {
        this.bigAppIcon = bigAppIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getApplevel() {
        return applevel;
    }

    public void setApplevel(int applevel) {
        this.applevel = applevel;
    }

    public long getDownloads() {
        return downloads;
    }

    public void setDownloads(long downloads) {
        this.downloads = downloads;
    }

    public String getDownloadCountStr() {
        return downloadCountStr;
    }

    public void setDownloadCountStr(String downloadCountStr) {
        this.downloadCountStr = downloadCountStr;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAppMemo() {
        return appMemo;
    }

    public void setAppMemo(String appMemo) {
        this.appMemo = appMemo;
    }

    public AppItemInfo test(int id){
        appId=id;
        appIcon="";
        bigAppIcon="http://baidu.com/xxx.png";
        appName="test app";
        packageName="com.test.app";
        versionCode=32111;
        versionName="test version";
        applevel=1;
        downloads=1000;
        downloadCountStr="sdfsf";
        appSize=10000;
        appType="游戏";
        md5="osokxdfdf";
        downloadUrl="下载地址";
        appMemo="只是测试app";
        return this;
    }

}

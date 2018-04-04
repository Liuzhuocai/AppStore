package com.elf.appstore.model.entities;

/**
 * Created by antino on 18-3-22.
 */

public class BannerInfo {
    //TODO:antino 商店首页的横幅信息
    private int bannerId;
    private String bannerName;
    private String iconPath;
    private String bannerUrl;
    private String bannerType;//广告、资讯、应用等等，可能根据这个做不同的跳转

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public BannerInfo test(int id ){
        bannerId=id;
        bannerName="fuck";
        iconPath="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522492548540&di=fa6bd65cf4763cc6578c21c0e68b3772&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F12%2F80%2F81%2F34bOOOPICb2_1024.jpg";
        bannerUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522492548540&di=fa6bd65cf4763cc6578c21c0e68b3772&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F12%2F80%2F81%2F34bOOOPICb2_1024.jpg";
        bannerType="banner类型";
        return this;
    }
}

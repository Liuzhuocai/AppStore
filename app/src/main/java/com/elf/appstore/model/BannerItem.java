package com.elf.appstore.model;

import com.elf.appstore.model.entities.BannerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antino on 18-3-26.
 */

public class BannerItem extends BaseItem {
    List<BannerInfo> banners;

    public BannerItem() {
        itemType = TYPE_BANNER;
    }

    public List<BannerInfo> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerInfo> banners) {
        this.banners = banners;
    }

    public List<String> getTittles() {
        ArrayList<String> tittles = new ArrayList<>();
        for (BannerInfo info : banners) {
            tittles.add(info.getBannerUrl());
        }
        return tittles;
    }

    public List<String> getUrls() {
        ArrayList<String> urls = new ArrayList<>();
        for (BannerInfo info : banners) {
            urls.add(info.getBannerUrl());
        }
        return urls;
    }

    public boolean isEmpty() {
        if (banners != null && banners.size() > 0) {
            return false;
        }
        return true;
    }
}

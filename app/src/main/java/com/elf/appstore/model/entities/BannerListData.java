package com.elf.appstore.model.entities;

import java.util.List;

/**
 * Created by antino on 18-4-2.
 */

public class BannerListData {
    public List<BannerInfo> banners;
    public boolean isEmpty(){
        if(banners!=null&&banners.size()>0){
            return false;
        }
        return true;
    }
    public List<BannerInfo> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerInfo> banners) {
        this.banners = banners;
    }

}

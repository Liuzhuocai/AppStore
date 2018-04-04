package com.elf.appstore.adapter.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.elf.appstore.R;
import com.elf.appstore.adapter.base.CommonViewHolder;
import com.elf.appstore.http.PicassoImageLoader;
import com.elf.appstore.model.BannerItem;
import com.elf.appstore.model.entities.BannerInfo;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antino on 18-4-2.
 */

public class BannerViewHolder extends CommonViewHolder<BannerItem> {
    public BannerViewHolder(View itemView) {
        super(itemView);
    }
    @Override
    public void bindData(BannerItem data) {
        Banner banner = (Banner)getView(R.id.banner);

        if(banner!=null){
            List<String> urls = data.getUrls();
            if(urls.size()>0) {
                banner.setImages(urls).setImageLoader(new PicassoImageLoader()).start();
            }
            List<String> tittles = data.getTittles();
            if(tittles!=null&&tittles.size()>0){
                banner.setBannerTitles(tittles);
            }
        }
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerInfo info = data.getBanners().get(position);
                if(info !=null){
                    //TODO:antino
                    Toast.makeText(itemView.getContext(),"Banner item : "+info.toString(),Toast.LENGTH_SHORT).show();
                    //Getting  the detail banner refer to .
                    commonClickListener.onBannerClickListener(info);
                }
            }
        });
    }
}

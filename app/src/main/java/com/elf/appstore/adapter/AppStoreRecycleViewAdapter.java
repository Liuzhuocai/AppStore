package com.elf.appstore.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elf.appstore.R;
import com.elf.appstore.adapter.base.CommonViewHolder;
import com.elf.appstore.adapter.viewholder.BannerViewHolder;
import com.elf.appstore.adapter.viewholder.CategoryViewHolder;
import com.elf.appstore.adapter.viewholder.NetAppViewHolder;
import com.elf.appstore.http.PicassoImageLoader;
import com.elf.appstore.model.BannerItem;
import com.elf.appstore.model.BaseItem;
import com.elf.appstore.model.CategoryItem;
import com.elf.appstore.model.NetAppItem;
import com.elf.appstore.model.entities.BannerInfo;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antino on 18-3-30.
 */

public class AppStoreRecycleViewAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    //Item Type
    List<BaseItem> mData = new ArrayList<BaseItem>();

    private CommonViewHolder.OnItemCommonClickListener listener;
    public AppStoreRecycleViewAdapter(CommonViewHolder.OnItemCommonClickListener listener){
        super();
        this.listener = listener;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BaseItem.TYPE_BANNER:
                return new BannerViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vh_banner, parent, false));
            case BaseItem.TYPE_CATEGORY:
                return new CategoryViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vh_category, parent, false));
            case BaseItem.TYPE_NET_APP:
                return new NetAppViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vh_app_item, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.bindData(mData.get(position));
        holder.setCommonClickListener(listener);
    }

    public void setData(List<BaseItem> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        int type = mData.get(position).getItemType();
        return type;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addAll(List<BaseItem> list) {
        int lastIndex = this.mData.size();
        if (this.mData.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
}

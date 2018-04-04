package com.elf.appstore.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.elf.appstore.R;
import com.elf.appstore.adapter.base.CommonViewHolder;
import com.elf.appstore.adapter.viewholder.NetAppViewHolder;
import com.elf.appstore.model.ApplistData;
import com.elf.appstore.model.entities.AppItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antino on 18-4-3.
 */

public class CategoryListAdpater<T> extends RecyclerView.Adapter<CommonViewHolder>{
    List<T> list = new ArrayList<T>();
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NetAppViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vh_app_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<T> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data){
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
    }
}

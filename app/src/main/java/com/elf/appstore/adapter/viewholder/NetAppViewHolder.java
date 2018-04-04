package com.elf.appstore.adapter.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elf.appstore.R;
import com.elf.appstore.adapter.base.CommonViewHolder;
import com.elf.appstore.model.NetAppItem;
import com.squareup.picasso.Picasso;

/**
 * Created by antino on 18-4-2.
 */

public class NetAppViewHolder extends CommonViewHolder<NetAppItem> {
    public NetAppViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(NetAppItem data) {
        ImageView appIcon = (ImageView) getView(R.id.app_icon);
        TextView appName = (TextView) getView(R.id.app_name);
        TextView appMemo = (TextView) getView(R.id.app_meno);
        Button bt = (Button) getView(R.id.app_download);
        itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v.getId() == R.id.app_download){
                            commonClickListener.onDownloadClick(data);
                        }else{
                            commonClickListener.onItemClickListener(data);
                        }
                    }
                }
        );
        Picasso.with(appIcon.getContext())
                .load(data.appItemInfo.getBigAppIcon())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder_loading)
                .error(R.drawable.placeholder_error)
                .into(appIcon);
        appName.setText(data.appItemInfo.getAppName());
        appMemo.setText(data.appItemInfo.getAppMemo());
        Log.i("xxxx", "NetAppViewHolder setData = " + " , ");
    }
}
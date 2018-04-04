package com.elf.appstore.http;

import android.content.Context;
import android.widget.ImageView;

import com.elf.appstore.R;
import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by antino on 18-3-30.
 */

public class PicassoImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Picasso.with(context)
                .load((String)path)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder_loading)
                .error(R.drawable.placeholder_error)
                .into(imageView);
    }
}

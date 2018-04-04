package com.elf.appstore.account.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * Created by liuzuocai on 18-3-23.
 */

public abstract class BasePageLayout {
    LinearLayout layout = null;
    Context context = null;
    LayoutInflater mLayoutInflater =null;
    public BasePageLayout(Context c, boolean isSearch) {
        this.mLayoutInflater = LayoutInflater.from(c);
        this.context = c;
        this.layout = new LinearLayout(this.context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        this.layout.setLayoutParams(params);
        this.layout.setOrientation(LinearLayout.VERTICAL);
        this.layout.setBackgroundColor(-1);
        LinearLayout title = TitleLayout.create(this.context, isSearch);
        this.layout.addView(title);
        this.onCreateContent(this.layout);
    }

    public LinearLayout getLayout() {
        return this.layout;
    }

    protected abstract void onCreateContent(LinearLayout var1);
}
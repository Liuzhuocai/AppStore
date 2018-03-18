package com.acuteangle.appstore;

import android.app.Application;

import com.mob.MobSDK;

/**
 * 创建于 2018/3/17 18:15.
 * 作者liuzuo
 */

public class StoreApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化ShareSDK
        MobSDK.init(this);
    }
}

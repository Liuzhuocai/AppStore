package com.elf.appstore.http;

import android.content.Context;
import com.elf.appstore.config.NetConfig;
import com.elf.appstore.model.ApplistData;
import com.elf.appstore.model.BannerItem;
import com.elf.appstore.model.entities.AppDetailInfo;
import com.elf.appstore.model.entities.BannerListData;
import com.elf.appstore.model.entities.HomeCategoryData;

import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by antino on 18-3-26.
 */

public class HttpHelper {
    public static HttpHelper helper;
    private OkHttpClient mClient;
    private static final long cacheSize = 1024 * 1024 * 20;// 缓存文件最大限制大小20M


    private HttpHelper(Context context) {
        String cacheDirectory = context.getCacheDir() + "/cache"; // 设置缓存文件路径
        Cache cache = new Cache(new File(cacheDirectory), cacheSize);
        mClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .cache(cache)
                .build();

    }

    public static synchronized HttpHelper getInstance(Context context) {
        if (helper == null) {
            helper = new HttpHelper(context);
        }
        return helper;
    }

    //获取app广告位
    public void getBannar(Context context, final DataResponse<BannerItem> response) {
        CacheControl control = new CacheControl.Builder()
                .maxAge(60, TimeUnit.SECONDS)
                .maxStale(60,TimeUnit.SECONDS)
                .minFresh(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(NetConfig.URL_BANNER)
                .cacheControl(control)
                .build();
        Call call = mClient.newCall(request);
        OKHttpJsonCallback callback = new OKHttpJsonCallback()
                .callback(new BaseJsonParseCallback(response, BannerItem.class))
                .setReCall(call);
        call.enqueue(callback);
    }

    //获取主页分类列表
    public void getHomeCategory(final Context context, int pageNum, int pageSize,final DataResponse<HomeCategoryData> response) {
        CacheControl control = new CacheControl.Builder()
                .maxAge(60, TimeUnit.SECONDS)
                .maxStale(60,TimeUnit.SECONDS)
                .minFresh(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(NetConfig.URL_HOME_CATEGORY)
                .cacheControl(control)
                .build();
        Call call = mClient.newCall(request);
        OKHttpJsonCallback callback = new OKHttpJsonCallback()
                .callback(new BaseJsonParseCallback(response, HomeCategoryData.class))
                .setReCall(call);
        call.enqueue(callback);
    }

    //获取app详情
    public void getAppDetail(final Context context, String packageName,final DataResponse<AppDetailInfo> response) {
        CacheControl control = new CacheControl.Builder()
                .maxAge(60, TimeUnit.SECONDS)
                .maxStale(60,TimeUnit.SECONDS)
                .minFresh(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(NetConfig.URL_APPDETAIL)
                .cacheControl(control)
                .build();
        Call call = mClient.newCall(request);
        OKHttpJsonCallback callback = new OKHttpJsonCallback()
                .callback(new BaseJsonParseCallback(response, AppDetailInfo.class))
                .setReCall(call);
        call.enqueue(callback);
    }

    /**
     * 获取分类详情数据
     * @param context
     * @param pageNum
     * @param pageSize
     * @param response
     */
    public void getHomeCategoryDetail(final Context context, int pageNum, int pageSize,int appType,final DataResponse<ApplistData> response) {
        CacheControl control = new CacheControl.Builder()
                .maxAge(60, TimeUnit.SECONDS)
                .maxStale(60,TimeUnit.SECONDS)
                .minFresh(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(NetConfig.URL_CATEGORY_DETAIL)
                .cacheControl(control)
                .build();
        Call call = mClient.newCall(request);
        OKHttpJsonCallback callback = new OKHttpJsonCallback()
                .callback(new BaseJsonParseCallback(response, ApplistData.class))
                .setReCall(call);
        call.enqueue(callback);
    }

    public void getTestData(Context context, Callback callback){
        CacheControl control = new CacheControl.Builder()
                .maxAge(60, TimeUnit.SECONDS)
                .maxStale(60,TimeUnit.SECONDS)
                .minFresh(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(NetConfig.URL_TEST)
                .cacheControl(control)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(callback);

    }
}

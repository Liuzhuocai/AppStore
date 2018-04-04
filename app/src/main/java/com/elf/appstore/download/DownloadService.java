package com.elf.appstore.download;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.elf.appstore.model.DownloadTaskRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antino on 18-3-23.
 */

public class DownloadService extends Service{
    public static final String DOWNLOAD_RECORD ="download_record";
    public static final String DOWNLOAD_OPERATION = "download_operation"; // 下载操作
    public static final int OPERATION_START_DOWNLOAD = 100; // 开始下载
    public static final int OPERATION_PAUSE_DOWNLOAD = 101; // 暂停下载
    public static final int OPERATION_CONTINUE_DOWNLOAD = 102; // 继续下载
    public static final int OPERATION_PAUSE_CONTINUE_DOWNLOAD = 103; // 继续或暂停下载
    public static final int OPERATION_CANCEL_DOWNLOAD = 104; // 取消下载
    public static final int OPERATION_NETWORK_CHANGE = 105; // 网络改变
    public static final int OPERATION_NETWORK_MOBILE_PAUSE = 106; // 网络改变为手机网络需要暂停
    public static final int OPERATION_NETWORK_MOBILE_CONTINUE = 107; // 网络改变为手机网络需要继续
    public static final int OPERATION_CHECK = 108;	// 检查并打开服务(会把处于下载中的任务全部暂停)
    public static final int OPERATION_START_DOWNLOAD_LIST_CLOUD = 109;	// 从云服务过来的列表下载
    private static final int HANDEL_CHECK = 200; // 处理检查操作
    DownloadManager dm;
    List<ServiceListener> listeners = new ArrayList<ServiceListener>();
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO:antino 需要对下载中的任务罗列让他们在后台下载
        dm = DownloadManager.getInstance();
        dm.addServiceListener(serviceListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                DownloadTaskRecord record = (DownloadTaskRecord)bundle.get(DOWNLOAD_RECORD);
                int operation = bundle.getInt(DOWNLOAD_OPERATION);
                handleDownloadOp(record,operation);
            }
        }
        return super.onStartCommand(intent, flags, startId);

    }

    private void handleDownloadOp(DownloadTaskRecord record,int operation){
        switch (operation){
            // 开始下载
            case OPERATION_START_DOWNLOAD:
                break;
            case OPERATION_PAUSE_DOWNLOAD:
                break;
            case OPERATION_CONTINUE_DOWNLOAD:
                break;
            case OPERATION_PAUSE_CONTINUE_DOWNLOAD:
                break;
            case OPERATION_CANCEL_DOWNLOAD:
                break;
            case OPERATION_NETWORK_CHANGE:
                //网络改变时
                //1.有网络，需要把无网络时暂停的任务重启
                //2.无网络，需要把任务设置为无网络状态
                break;
            case OPERATION_NETWORK_MOBILE_PAUSE:
                //移动网络改变时，需要对用户提示是否重新下载
                //如果用户选择不继续下载，这需要暂停下载应用
                break;
            case OPERATION_NETWORK_MOBILE_CONTINUE:
                // 找到网络错误状态的任务, 开始进行下载
                break;
            case OPERATION_CHECK:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //just for UI
    public void addListener(ServiceListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void removeListener(ServiceListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    //Let DownloadManager to regist only
    ServiceListener serviceListener = new ServiceListener() {
        @Override
        public void onUpdate(DownloadTaskRecord record) {
            for (ServiceListener l : listeners) {
                l.onUpdate(record);
            }
        }
    };

    public interface ServiceListener{
        void onUpdate(DownloadTaskRecord record);
    }
}

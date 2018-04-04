package com.elf.appstore.download;

import android.util.Log;

import com.elf.appstore.model.DownloadTaskRecord;
import com.elf.appstore.model.entities.AppDetailInfo;
import com.elf.appstore.model.entities.AppItemInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.elf.appstore.download.DownloadTask.TAG;

/**
 * Created by antino on 18-3-20.
 */

public class DownloadManager implements DownloadListener{
    private static final AtomicReference<DownloadManager> INSTANCE = new AtomicReference<>();
    private Map<String, DownloadTask> mDownloadTasks = new LinkedHashMap<String, DownloadTask>();
    private List<DownloadService.ServiceListener> mServiceListener = new ArrayList<>();
    public static DownloadManager getInstance() {
        for (; ; ) {
            DownloadManager current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new DownloadManager();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    private DownloadManager() {
    }

    public void download(DownloadTaskRecord record){
        if(isExist(record)){
            Log.i(TAG,"the download task is exist:"+record.toString());
            return;
        }
        DownloadTask task = new DownloadTask(record,this);
        ThreadPoolManager.getDownloadPool().execute(task);
    }

    private boolean isExist(DownloadTaskRecord record) {
        return false;
    }

    private DownloadTaskRecord newDownloadInfo(AppItemInfo bean) {
        return null;
    }

    private void beanToTaskRecord(DownloadTaskRecord info, AppItemInfo bean) {
        //TODO:antino translate bean info to download info.
    }

    private String getAppPath(String label) {
        //TODO:antino Need get app download path
        return null;
    }

    public DownloadTaskRecord getTaskRecord(AppItemInfo bean){
        return null;
    }

    private boolean isInstalled(String packageName) {
        return false;
    }

    private long getContentLength(String downloadUrl) {
        return -1;
    }

    public void cancel(AppItemInfo bean){
    }

    public void pause(AppItemInfo bean){
    }

    public void addServiceListener(DownloadService.ServiceListener listener){
        if(!mServiceListener.contains(listener)){
            mServiceListener.add(listener);
        }
    }
    public void removeServiceLisener(DownloadService.ServiceListener listener){
        if(mServiceListener.contains(listener)){
            mServiceListener.remove(listener);
        }
    }

    @Override
    public void onStateChanged(DownloadTaskRecord record) {
        for(DownloadService.ServiceListener s :mServiceListener){
            s.onUpdate(record);
        }
    }
}

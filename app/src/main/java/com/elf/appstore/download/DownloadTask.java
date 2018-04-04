package com.elf.appstore.download;
import android.util.Log;

import com.elf.appstore.model.DownloadTaskRecord;
import com.elf.appstore.utils.IOUtils;
import com.elf.appstore.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by antino on 18-3-22.
 */

public class DownloadTask implements Runnable {
    public static final String TAG = "Download";
    DownloadTaskRecord record;
    OkHttpClient mClient;
    private boolean isPaused=false;
    private boolean needRetry= false;

    DownloadListener listener;

    DownloadTask(DownloadTaskRecord record) {
        this.record = record;
    }

    DownloadTask(DownloadTaskRecord record, DownloadListener listener) {
        this.record = record;
        this.listener = listener;
    }

    @Override
    public void run() {
        mClient = new OkHttpClient();
        startDownload();
    }

    private void startDownload() {
        //判断网络状态
        //获取要下载的文件打小
        long fileSize = getFileSize(record.getDownloadUrl());
        if(fileSize==-1){
            return;
        }
        record.setFileSize(fileSize);
        //获取改文件是否已经下载
        long range = 0;
        long loadedSize = 0;
        File file = new File(record.getFileDir(),record.getFileName());
        if (file.exists() && file.length() < fileSize) {
            loadedSize = range = file.length();
        }

        Request request = new Request.Builder()
                .addHeader("RANGE", "bytes=" + range + "-" + fileSize)
                .url(record.getDownloadUrl())
                .build();
        Call call = mClient.newCall(request);
        RandomAccessFile accessFile = null;
        InputStream is = null;
        try {
            Response response = call.execute();
            if(response.isSuccessful()){
                is = response.body().byteStream();
                accessFile = new RandomAccessFile(file, "rw");
                accessFile.seek(range);
                int offset = 0;
                byte[] buffer = new byte[1024];
                while ((offset = is.read(buffer)) != -1) {
                    if(isPaused){
                        call.cancel();
                        record.setStatus(DownloadState.STATE_PAUSE);
                        break;
                    }
                    accessFile.write(buffer, 0, offset);
                    loadedSize += offset;
                    record.setLoadedSize(loadedSize);
                    updateTaskState();
                }

                if(isPaused){
                    record.setStatus(DownloadState.STATE_PAUSE);
                }else{
                    if (file.length() == loadedSize) {
                        Log.d(TAG, "结束：成功");
                        record.setStatus(DownloadState.STATE_DOWNLOADED);
                    } else {
                        Log.d(TAG, "结束：失败");
                        // 删除文件
                        if (file != null) {
                            file.delete();
                        }
                        record.setStatus(DownloadState.STATE_FAILED);
                    }
                    updateTaskState();
                }
            }else{
                LogUtil.e(TAG,"Request Fail :"+response.code());
                record.setStatus(DownloadState.STATE_FAILED);
            }

        } catch (IOException e) {
            // 删除文件
            if (file != null) {
                file.delete();
            }
            LogUtil.e(TAG,"Request Fail :"+e.getStackTrace().toString());
            record.setStatus(DownloadState.STATE_FAILED);
            updateTaskState();
        }finally {
            IOUtils.closeAll(accessFile,is);
        }

    }

    private void pause(){
        isPaused=true;

    }

    private void cancel(){
        isPaused = true;
    }

    private long getFileSize(String url){
        return  -1;
    }

    private File getRealFile(DownloadTaskRecord record) {

        return null;
    }

    private void updateTaskState() {
        listener.onStateChanged(record);
    }
}

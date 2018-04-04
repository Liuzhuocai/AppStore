package com.elf.appstore.download;

/**
 * Created by antino on 18-3-22.
 */

public class DownloadState {
    public static final String TAG = DownloadManager.class.getSimpleName();
    public final static int STATE_UNDOWNLOAD = 0;                                            // 未下载状态
    public final static int STATE_WAITTING = 1;                                            // 等待状态
    public final static int STATE_DOWNLOADING = 2;                                            // 下载中状态
    public final static int STATE_PAUSE = 3;                                            // 暂停状态
    public final static int STATE_DOWNLOADED = 4;                                            // 下载完成

    public final static int STATE_INSTALLED = 5;                                            // 安装完成状态
    public final static int STATE_FAILED = 6;                                            // 下载失败状态
    public final static int STATE_NO = 7;                                                   // 无资源

    public static final int STATUS_DEFAULT = 0; // 默认
    public static final int STATUS_WAIT = 1; // 等待
    public static final int STATUS_CONNECTING = 2; // 连接
    public static final int STATUS_DOWNLOADING = 3; // 正在下载
    public static final int STATUS_PAUSE = 4; // 暂停
    public static final int STATUS_PAUSE_NEED_CONTINUE = 5; // 暂停需要继续
    public static final int STATUS_NO_NETWORK = 6; // 暂无网络
    public static final int STATUS_CONNECT_TIMEOUT = 7; // 连接超时
    public static final int STATUS_CONNECT_RETRY = 8; // 重试
    public static final int STATUS_FAIL = 9; // 下载失败
    public static final int STATUS_INSTALL_WAIT = 10; // 安装等待
    public static final int STATUS_INSTALLING = 11; // 正在安装
    public static final int STATUS_INSTALLFAILED = 12; // 安装失败
    public static final int STATUS_INSTALLED = 13; // 安装完成

}

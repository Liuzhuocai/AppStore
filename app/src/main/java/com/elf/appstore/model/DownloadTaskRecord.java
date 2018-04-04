package com.elf.appstore.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by antino on 18-3-22.
 */

public class DownloadTaskRecord implements Parcelable {
    private String taskId; // 唯一ID,用于标示下砸任务,包名+版本号组合. 例如:com.monster.market_1
    private int apkId; // 软件ID，可用在发送通知图标用
    private String apkName; // 软件名字，显示在UI上
    private String downloadUrl; // 下载地址
    private String versionName; // 版本，用于对比手机上的
    private int versionCode; // 版本码，用于对比手机上的
    private String packageName; // 包名，用于检查手机是否已安装
    private String apkLogoPath; // 图标位置
    // 以下字段只有在数据库查找时才会有
    private int status; // 下载状态
    private String fileDir; // 文件存放目录
    private String fileName; // 文件名称
    private long finishTime; // 任务完成时间
    private long fileSize;
    private long loadedSize;
    // 以下字段为豌豆荚合作需要的字段 作为预留暂时不用
    private String pos;		// 应用展示位置
    private String download_type = "download";	// 是更新还是自然下载 download update


    protected DownloadTaskRecord(Parcel in) {
        taskId = in.readString();
        apkId = in.readInt();
        apkName = in.readString();
        downloadUrl = in.readString();
        versionName = in.readString();
        versionCode = in.readInt();
        packageName = in.readString();
        apkLogoPath = in.readString();
        status = in.readInt();
        fileDir = in.readString();
        fileName = in.readString();
        finishTime = in.readLong();
        pos = in.readString();
        download_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskId);
        dest.writeInt(apkId);
        dest.writeString(apkName);
        dest.writeString(downloadUrl);
        dest.writeString(versionName);
        dest.writeInt(versionCode);
        dest.writeString(packageName);
        dest.writeString(apkLogoPath);
        dest.writeInt(status);
        dest.writeString(fileDir);
        dest.writeString(fileName);
        dest.writeLong(finishTime);
        dest.writeString(pos);
        dest.writeString(download_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DownloadTaskRecord> CREATOR = new Creator<DownloadTaskRecord>() {
        @Override
        public DownloadTaskRecord createFromParcel(Parcel in) {
            return new DownloadTaskRecord(in);
        }

        @Override
        public DownloadTaskRecord[] newArray(int size) {
            return new DownloadTaskRecord[size];
        }
    };
    public String getAppPath(){
        return this.fileDir+this.fileName;
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getApkId() {
        return apkId;
    }

    public void setApkId(int apkId) {
        this.apkId = apkId;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApkLogoPath() {
        return apkLogoPath;
    }

    public void setApkLogoPath(String apkLogoPath) {
        this.apkLogoPath = apkLogoPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDownload_type() {
        return download_type;
    }

    public void setDownload_type(String download_type) {
        this.download_type = download_type;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getLoadedSize() {
        return loadedSize;
    }

    public void setLoadedSize(long loadedSize) {
        this.loadedSize = loadedSize;
    }
}

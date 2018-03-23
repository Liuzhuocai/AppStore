package com.elf.appstore.account;

/**
 * Created by liuzuocai on 18-3-20.
 */

public interface ILoginModel {
    //提供数据
     String getInfo();

    //存储数据
     void setInfo(String info);

    int getNowDiaLogString(int type);
}

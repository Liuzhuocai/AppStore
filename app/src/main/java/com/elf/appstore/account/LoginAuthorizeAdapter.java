package com.elf.appstore.account;

import cn.sharesdk.framework.authorize.AuthorizeAdapter;

/**
 * Created by liuzuocai on 18-3-20.
 */

public class LoginAuthorizeAdapter extends AuthorizeAdapter {
    @Override
    public void onCreate() {
        System.out.println("> ShareSDKUIShell created!");
////获取标题栏控件
//        TitleLayout llTitle = getTitleLayout();
////标题栏的文字修改
//        int resID=getStringRes(getActivity(), "second_title");//这个字段定义在strings.xml文件里面
//        llTitle.getTvTitle().setText(resID);
        hideShareSDKLogo();
        super.onCreate();
    }
}

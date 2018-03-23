package com.elf.appstore.account;

import com.elf.appstore.R;

import cn.sharesdk.framework.Platform;

import static com.elf.appstore.account.LoginActivity.FACEBOOKTYPE;
import static com.elf.appstore.account.LoginActivity.QQTYPE;
import static com.elf.appstore.account.LoginActivity.SINATYPE;
import static com.elf.appstore.account.LoginActivity.TWITTERTYPE;
import static com.elf.appstore.account.LoginActivity.WECHATTYPE;

/**
 * Created by liuzuocai on 18-3-20.
 */

public class LoginModelImpl implements ILoginModel{

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String info) {

    }

    public int  getNowDiaLogString(int type) {
        int  dialogTextRes = -1;
        switch (type) {
            case WECHATTYPE:

                dialogTextRes = R.string.opening_wechat;

                break;
            case QQTYPE:
                dialogTextRes = R.string.opening_qq;
                break;
            case SINATYPE:
                dialogTextRes =  R.string.opening_blog;
                break;
            case FACEBOOKTYPE:
                dialogTextRes =  R.string.opening_facebook;
                break;
            case TWITTERTYPE:
                dialogTextRes =  R.string.opening_twitter;
                break;
            default:
                break;
        }
        return dialogTextRes;
    }
    //授权
    private void authorize(Platform plat, int type) {
        if (plat.isAuthValid()) { //如果授权就删除授权资料
            plat.removeAccount(true);
        }
        plat.showUser(null);//授权并获取用户信息

    }

}

package com.elf.appstore.account;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.R.attr.action;
import static com.elf.appstore.account.LoginActivity.FACEBOOKTYPE;
import static com.elf.appstore.account.LoginActivity.QQTYPE;
import static com.elf.appstore.account.LoginActivity.SINATYPE;
import static com.elf.appstore.account.LoginActivity.TWITTERTYPE;
import static com.elf.appstore.account.LoginActivity.WECHATTYPE;

/**
 * Created by liuzuocai on 18-3-20.
 */

public class LoginPresenter implements PlatformActionListener, Handler.Callback {

    ILoginView mLoginView;
    ILoginModel mLoginModel;


    private static final int MSG_ACTION_CCALLBACK = 0;

    public LoginPresenter(ILoginView loginView) {
        mLoginView = loginView;
        mLoginModel = new LoginModelImpl();
        registerSms();
    }

    private void registerSms() {

        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    showToastFromCallback("发送成功");

                } else{
                    Log.d("liuzuo","event = "+event+"  data:"+data.toString());
                    // TODO 处理错误的结果
                    showToastFromCallback("手机号有误 result="+result);
                }

            }
        });

        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    showToastFromCallback("验证成功");
                } else{
                    // TODO 处理错误的结果
                    showToastFromCallback("验证失败");
                }

            }
        });

    }

    private void showWaitingDialog(int diaLogRes) {
        if (mLoginView != null) {
            mLoginView.showWaitingDialog(diaLogRes);
        }
    }

    private void dissmissWaitingDialog() {
        if (mLoginView != null) {
            mLoginView.dismissWaitingDialog();
        }
    }

    private void saveInfo(String info) {
        mLoginModel.setInfo(info);
    }

    private String localInfo() {
        return mLoginModel.getInfo();
    }



    public void authorize(int type) {
        int diaLogRes = mLoginModel.getNowDiaLogString(type);
        showWaitingDialog(diaLogRes);
        switch (type) {
            case WECHATTYPE:
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(this);
                wechat.SSOSetting(false);
                authorizeLogin(wechat, 1);
                break;
            case QQTYPE:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(this);
                qq.SSOSetting(false);
                authorizeLogin(qq, 2);
                break;
            case SINATYPE:
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                sina.setPlatformActionListener(this);
                sina.SSOSetting(false);
                authorizeLogin(sina, 3);
                break;
            case FACEBOOKTYPE:
                Platform facebook = ShareSDK.getPlatform(Facebook.NAME);
                facebook.setPlatformActionListener(this);
                facebook.SSOSetting(false);
                authorizeLogin(facebook, 4);
                break;
            case TWITTERTYPE:
                Platform twitter = ShareSDK.getPlatform(Twitter.NAME);
                twitter.setPlatformActionListener(this);
                twitter.SSOSetting(false);
                authorizeLogin(twitter, 5);
                break;
            default:
                break;
        }
    }

    private void authorizeLogin(Platform platform, int type) {
        if (platform.isAuthValid()) { //如果授权就删除授权资料
            platform.removeAccount(true);
        }
        platform.showUser(null);//授权并获取用户信息
    }



    //登陆授权成功的回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> res) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);   //发送消息

    }

    //登陆授权错误的回调
    @Override
    public void onError(Platform platform, int i, Throwable t) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    //登陆授权取消的回调
    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    //登陆发送的handle消息在这里处理
    @Override
    public boolean handleMessage(Message message) {
        dissmissWaitingDialog();
        String toastText = null;
        switch (message.arg1) {
            case 1: { // 成功
                toastText = "授权登陆成功";
                //获取用户资料
                Platform platform = (Platform) message.obj;
                String userId = platform.getDb().getUserId();//获取用户账号
                String userName = platform.getDb().getUserName();//获取用户名字
                String userIcon = platform.getDb().getUserIcon();//获取用户头像
                String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
//                Toast.makeText(LoginActivity.this, "用户信息为--用户名：" + userName + "  性别：" + userGender, Toast.LENGTH_SHORT).show();
                String accessToken = platform.getDb().getToken(); // 获取授权token
                String openId = platform.getDb().getUserId(); // 获取用户在此平台的ID
//部分没有封装的字段可以通过键值获取，例如下面微信的unionid字段
                Log.d("liuzuo","openId:"+openId);
                Log.d("liuzuo","userId:"+userId);
                Log.d("liuzuo","userName:"+userName);
                Log.d("liuzuo","userIcon:"+userIcon);
                Log.d("liuzuo","userGender:"+userGender);
                //下面就可以利用获取的用户信息登录自己的服务器或者做自己想做的事啦!
                //。。。

            }
            break;
            case 2: { // 失败
                toastText = "授权登陆失败";
            }
            break;
            case 3: { // 取消
                toastText = "授权登陆取消";
            }
            case 6: { //toast
                if(message.obj instanceof String) {
                    toastText = (String) message.obj;
                }
            }
            break;
            case 7: { //tips
                if(message.obj instanceof String) {
                    showTips((String) message.obj);
                }
            }
            break;

            default:
                break;
        }
        if(toastText!=null){
            mLoginView.showToast(toastText);
        }
        return false;
    }
    private void showToastFromCallback(String toastText){
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 6;
        msg.arg2 = action;
        msg.obj = toastText;
        UIHandler.sendMessage(msg, this);   //发送消息
    }
    public void showTipsFromCallback(String tipsText){
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 7;
        msg.arg2 = action;
        msg.obj = tipsText;
        UIHandler.sendMessage(msg, this);   //发送消息
    }
    private void showTips(String tips){
        mLoginView.showTips(tips);
    }

    public void sendPhoneNum(String phoneOrEmail) {
        // 触发操作
        SMSSDK.getVerificationCode("86", phoneOrEmail.trim());
    }
    public void submitCode( String phone, String code) {

        // 触发操作
        SMSSDK.submitVerificationCode("86", phone, code);
    }

    public void unregisterAll() {
        SMSSDK.unregisterAllEventHandler();
    }

}


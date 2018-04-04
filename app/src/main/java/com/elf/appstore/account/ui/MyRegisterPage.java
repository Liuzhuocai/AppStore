package com.elf.appstore.account.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elf.appstore.R;
import com.elf.appstore.account.ILoginView;
import com.elf.appstore.account.LoginConfig;
import com.elf.appstore.account.LoginPresenter;
import com.elf.appstore.account.StringUtils;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;

import org.json.JSONObject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.UserInterruptException;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.CountryPage;
import cn.smssdk.utils.SMSLog;


/**
 * Created by liuzuocai on 18-3-23.
 */

public class MyRegisterPage extends FakeActivity implements View.OnClickListener, TextWatcher ,ILoginView {
    private static final String DEFAULT_COUNTRY_ID = "42";
    private EventHandler callback;
    private TextView tvCountry;
    private EditText etPhoneNum;
    private TextView tvCountryNum;
    private ImageView ivClear;
    private Button btnNext;
    private String currentId;
    private String currentCode;
    private EventHandler handler;
    private Dialog pd;
    private OnSendMessageHandler osmHandler;
    private LoginPresenter mPresenter;

    private TitleType mTitleType = TitleType.LOGIN_PWD ;


    private ImageView ivWxLogin;
    private ImageView ivQqLogin;
    private ImageView ivBlog;
    private ImageView ivFacebook;
    private ImageView ivTwitter;

    private TextView mLeftEnter;
    private TextView mRightEnter;
    //是否需要注册
    private Boolean isFirstRegister = false;
    private Boolean ingoreEvent = false;


    public static final int WECHATTYPE = 1;
    public static final int QQTYPE = 2;
    public static final int SINATYPE = 3;
    public static final int FACEBOOKTYPE = 4;
    public static final int TWITTERTYPE = 5;

    public   enum TitleType{
     LOGIN_PWD , LOGIN_VERIFICATION ,//登录界面类型
        VERIFICATION_LOGIN , VERIFICATION_REGISTER,VERIFICATION_RESET_PWD ,//验证码界面类型
        PWD_LOGIN,PWD_SETUP,PWD_RESET,PWD_INPUT_AGAIN,//密码输入界面类型
    }
    public MyRegisterPage() {
    }




    public void setRegisterCallback(EventHandler callback) {
        this.callback = callback;
    }

    public void setOnSendMessageHandler(OnSendMessageHandler h) {
        this.osmHandler = h;
    }

    public void show(Context context) {
        super.show(context, (Intent)null);
    }

    public void onCreate() {

        initPresenter();
        initView();
        initTitleType();
    }

    private void initTitleType() {
        if(mTitleType==TitleType.LOGIN_VERIFICATION){
            mRightEnter.setText(R.string.login_register_pwd_right);
        }
    }

    public void setTitleType(TitleType type) {
        this.mTitleType = type;
    }

    private void initView() {



        RegisterPageLayout page = new RegisterPageLayout(this.activity);
        LinearLayout layout = page.getLayout();
        if(layout != null) {
            this.activity.setContentView(layout);
            this.currentId = "42";

            ivWxLogin = this.activity.findViewById(R.id.iv_wx_login);
            ivQqLogin =  this.activity.findViewById(R.id.iv_qq_login);
            ivBlog = this.activity.findViewById(R.id.iv_blog);
            ivFacebook =  this.activity.findViewById(R.id.iv_facebook);
            ivTwitter =  this.activity.findViewById(R.id.iv_twitter);
            mLeftEnter =  this.activity.findViewById(R.id.login_register_enter_left);
            mRightEnter =  this.activity.findViewById(R.id.login_register_enter_right);

            View llBack = this.activity.findViewById(ResHelper.getIdRes(this.activity, "ll_back"));
            TextView tv = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_title"));
            int resId = ResHelper.getStringRes(this.activity, "smssdk_regist");
            if(resId > 0) {
                tv.setText(resId);
            }

            View viewCountry = this.activity.findViewById(ResHelper.getIdRes(this.activity, "rl_country"));
            this.btnNext = (Button)this.activity.findViewById(ResHelper.getIdRes(this.activity, "btn_next"));
            this.tvCountry = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_country"));
            String[] country = this.getCurrentCountry();
            if(country != null) {
                this.currentCode = country[1];
                this.tvCountry.setText(country[0]);
            }

            this.tvCountryNum = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_country_num"));
            this.tvCountryNum.setText("+" + this.currentCode);
            this.etPhoneNum = (EditText)this.activity.findViewById(ResHelper.getIdRes(this.activity, "et_write_phone"));
            this.etPhoneNum.setText("");
            this.etPhoneNum.addTextChangedListener(this);
            this.etPhoneNum.requestFocus();
            if(this.etPhoneNum.getText().length() > 0) {
                this.btnNext.setEnabled(true);
                this.ivClear = (ImageView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "iv_clear"));
                this.ivClear.setVisibility(View.VISIBLE);
                resId = ResHelper.getBitmapRes(this.activity, "smssdk_btn_enable");
                if(resId > 0) {
                    this.btnNext.setBackgroundResource(resId);
                }
            }

            this.ivClear = (ImageView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "iv_clear"));
            llBack.setOnClickListener(this);
            this.mLeftEnter.setOnClickListener(this);
            this.mRightEnter.setOnClickListener(this);
            this.btnNext.setOnClickListener(this);
            this.ivClear.setOnClickListener(this);
            viewCountry.setOnClickListener(this);
            this.handler = new EventHandler() {
                public void afterEvent(final int event, final int result, final Object data) {
                    MyRegisterPage.this.runOnUIThread(new Runnable() {
                        public void run() {
                            if(MyRegisterPage.this.pd != null && MyRegisterPage.this.pd.isShowing()) {
                                MyRegisterPage.this.pd.dismiss();
                            }

                            if(result == -1) {
                                if(event == 2&&!ingoreEvent) {
                                    boolean smart = ((Boolean)data).booleanValue();
                                    MyRegisterPage.this.afterVerificationCodeRequested(false);
                                }
                                ingoreEvent = false;
                            } else {
                                if(event == 2 && data != null && data instanceof UserInterruptException) {
                                    return;
                                }

                                int status = 0;

                                try {
                                    ((Throwable)data).printStackTrace();
                                    Throwable throwable = (Throwable)data;
                                    JSONObject object = new JSONObject(throwable.getMessage());
                                    String des = object.optString("detail");
                                    status = object.optInt("status");
                                    if(!TextUtils.isEmpty(des)) {
                                        Toast.makeText(MyRegisterPage.this.activity, des, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (Exception var5) {
                                    SMSLog.getInstance().w(var5);
                                }

                                int resIdx;
                                if(status >= 400) {
                                    resIdx = ResHelper.getStringRes(MyRegisterPage.this.activity, "smssdk_error_desc_" + status);
                                } else {
                                    resIdx = ResHelper.getStringRes(MyRegisterPage.this.activity, "smssdk_network_error");
                                }

                                if(resIdx > 0) {
                                    Toast.makeText(MyRegisterPage.this.activity, resIdx, Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
            };
        }

        ivWxLogin.setOnClickListener(this);
        ivQqLogin.setOnClickListener(this);
        ivBlog.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
    }

    private String[] getCurrentCountry() {
        String mcc = this.getMCC();
        String[] country = null;
        if(!TextUtils.isEmpty(mcc)) {
            country = SMSSDK.getCountryByMCC(mcc);
        }

        if(country == null) {
            SMSLog.getInstance().d("no country found by MCC: " + mcc, new Object[0]);
            country = SMSSDK.getCountry("42");
        }

        return country;
    }

    private String getMCC() {
        TelephonyManager tm = (TelephonyManager)this.activity.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tm.getNetworkOperator();
        return !TextUtils.isEmpty(networkOperator)?networkOperator:tm.getSimOperator();
    }

    public void onResume() {
        SMSSDK.registerEventHandler(this.handler);
    }

    public void onDestroy() {

    }

    public void onStop() {
        SMSSDK.unregisterEventHandler(this.handler);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int resId;
        if(s.length() > 0) {
            this.btnNext.setEnabled(true);
            this.ivClear.setVisibility(View.VISIBLE);
            resId = ResHelper.getBitmapRes(this.activity, "smssdk_btn_enable");
            if(resId > 0) {
                this.btnNext.setBackgroundResource(resId);
            }
        } else {
            this.btnNext.setEnabled(false);
            this.ivClear.setVisibility(View.GONE);
            resId = ResHelper.getBitmapRes(this.activity, "smssdk_btn_disenable");
            if(resId > 0) {
                this.btnNext.setBackgroundResource(resId);
            }
        }

    }

    public void afterTextChanged(Editable s) {
    }

    public void onClick(View v) {
        int id = v.getId();
        int idLlBack = ResHelper.getIdRes(this.activity, "ll_back");
        int idRlCountry = ResHelper.getIdRes(this.activity, "rl_country");
        int idBtnNext = ResHelper.getIdRes(this.activity, "btn_next");
        int idIvClear = ResHelper.getIdRes(this.activity, "iv_clear");
        String phone = this.etPhoneNum.getText().toString().trim().replaceAll("\\s*", "");
        String code = this.tvCountryNum.getText().toString().trim();
        if(id == idLlBack) {
            this.finish();
        } else if(id == idRlCountry) {
            CountryPage countryPage = new CountryPage();
            countryPage.setCountryId(this.currentId);
            countryPage.showForResult(this.activity, (Intent)null, this);
        } else if(id == idBtnNext) {
            //this.showDialog(phone, code);
            nextPage(phone,code);
        } else if(id == idIvClear) {
            this.etPhoneNum.getText().clear();
        }
        switch (id) {
            case R.id.iv_wx_login:
                mPresenter.authorize(WECHATTYPE);
                break;
            case R.id.iv_qq_login:
                mPresenter.authorize(QQTYPE);
                break;
            case R.id.iv_blog:
                mPresenter.authorize(SINATYPE);
                break;
            case R.id.iv_facebook:
                mPresenter.authorize(FACEBOOKTYPE);
                break;
            case R.id.iv_twitter:
                mPresenter.authorize(TWITTERTYPE);
                break;
            case R.id.login_register_enter_left:
                showDialog(phone,code,true);
                break;
            case R.id.login_register_enter_right:
                MyRegisterPage page = new MyRegisterPage();
                if(mTitleType==TitleType.LOGIN_VERIFICATION){
                    page.setTitleType(TitleType.LOGIN_PWD);
                    page.showForResult(this.activity, (Intent)null, this);
                    finish();
                }else {
                    page.setTitleType(TitleType.LOGIN_VERIFICATION);
                    page.showForResult(this.activity, (Intent)null, this);
                    finish();
                }

                break;
            default:
                break;
        }

    }

    private void nextPage(String phone, String code) {
        if(!StringUtils.isValidPhoneNumber(phone)){
            showToast("请输入正确的手机号");
            return;
        }

        if(MyRegisterPage.this.pd != null && MyRegisterPage.this.pd.isShowing()) {
            MyRegisterPage.this.pd.dismiss();
        }

//        MyRegisterPage.this.pd = CommonDialog.ProgressDialog(MyRegisterPage.this.activity);
//        if(MyRegisterPage.this.pd != null) {
//            MyRegisterPage.this.pd.show();
//        }

        SMSLog.getInstance().i("verification phone ==>>" + phone, new Object[0]);
        SMSLog.getInstance().i("verification tempCode ==>>1319972", new Object[0]);
        if(mTitleType==TitleType.LOGIN_VERIFICATION){
            SMSSDK.getVerificationCode(code, phone.trim(), MyRegisterPage.this.osmHandler);
        }else {

            afterVerificationCodeRequested(true);
        }
    }

    public void onResult(HashMap<String, Object> data) {
        if(data != null) {
            int page = ((Integer)data.get("page")).intValue();
            if(page == 1) {
                this.currentId = (String)data.get("id");
                String[] country = SMSSDK.getCountry(this.currentId);
                if(country != null) {
                    this.currentCode = country[1];
                    this.tvCountryNum.setText("+" + this.currentCode);
                    this.tvCountry.setText(country[0]);
                }
            } else if(page == 2) {
                Object res = data.get("res");
                HashMap<String, Object> phoneMap = (HashMap)data.get("phone");
                if(res != null && phoneMap != null) {
                    int resId = ResHelper.getStringRes(this.activity, "smssdk_your_ccount_is_verified");
                    if(resId > 0) {
                        Toast.makeText(this.activity, resId, Toast.LENGTH_SHORT).show();
                    }

                    if(this.callback != null) {
                        this.callback.afterEvent(3, -1, phoneMap);
                    }

                    this.finish();
                }
            }else if(page==3||page==4){
                Object pass = data.get("isPass");
                Object notStartUI = data.get("notStartUI");
                if(pass instanceof Boolean&&(boolean)pass){
                    finish();
                }else if(notStartUI instanceof Boolean&&(boolean)notStartUI){
                    ingoreEvent = true;
                }
            }
        }

    }

    private String splitPhoneNum(String phone) {
        StringBuilder builder = new StringBuilder(phone);
        builder.reverse();
        int i = 4;

        for(int len = builder.length(); i < len; i += 5) {
            builder.insert(i, ' ');
        }

        builder.reverse();
        return builder.toString();
    }

    public void showDialog(final String phone, final String code, boolean isRegister) {
        int resId = ResHelper.getStyleRes(this.activity, "login_dialog");
        if(resId > 0) {
            String phoneNum = code + " " + this.splitPhoneNum(phone);
            final Dialog dialog = new Dialog(this.getContext(),resId);
            //LinearLayout layout = SendMsgDialogLayout.create(this.getContext());
            View layout = this.activity.getLayoutInflater().inflate(R.layout.register_dialog, null);
            if(layout != null) {
                dialog.setContentView(layout);
                //((TextView)dialog.findViewById(ResHelper.getIdRes(this.activity, "tv_phone"))).setText(phoneNum);
                TextView tv = dialog.findViewById(R.id.login_register_dialog_text);
                resId = ResHelper.getStringRes(this.activity, "login_register_dialog_text");
                if(resId > 0) {
                    String text = this.getContext().getString(R.string.login_register_dialog_text,phoneNum);
                    tv.setText(text);
                    //tv.setText(Html.fromHtml(text));
                }

                (dialog.findViewById(ResHelper.getIdRes(this.activity, "login_register_dialog_ok"))).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        if(MyRegisterPage.this.pd != null && MyRegisterPage.this.pd.isShowing()) {
                            MyRegisterPage.this.pd.dismiss();
                        }

                        MyRegisterPage.this.pd = CommonDialog.ProgressDialog(MyRegisterPage.this.activity);
                        if(MyRegisterPage.this.pd != null) {
                            MyRegisterPage.this.pd.show();
                        }

                        SMSLog.getInstance().i("verification phone ==>>" + phone, new Object[0]);
                        SMSLog.getInstance().i("verification tempCode ==>>1319972", new Object[0]);
                        isFirstRegister = true;
                        SMSSDK.getVerificationCode(code, phone.trim(), MyRegisterPage.this.osmHandler);
                    }
                });
                (dialog.findViewById(ResHelper.getIdRes(this.activity, "login_register_dialog_cancel"))).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        }

    }

    private void afterVerificationCodeRequested(boolean isPwd) {
        String phone = this.etPhoneNum.getText().toString().trim().replaceAll("\\s*", "");
        String code = this.tvCountryNum.getText().toString().trim();
        if (code.startsWith("+")) {
            code = code.substring(1);
        }
        String formatedPhone = "+" + code + " " + this.splitPhoneNum(phone);
        switch (mTitleType) {
            case LOGIN_PWD:
                if(isFirstRegister){
                    IdentifyNumPage identifyNumPage = new IdentifyNumPage();
                    identifyNumPage.setPhone(phone, code, formatedPhone,TitleType.VERIFICATION_REGISTER);
                    identifyNumPage.showForResult(this.activity, (Intent) null, this);
                    isFirstRegister = false;
                }else {
                    PasswordNumPage page = new PasswordNumPage();
                    page.setPhone(phone, code, formatedPhone, TitleType.PWD_LOGIN, null);
                    page.showForResult(this.activity, (Intent) null, this);
                }

                break;
            case LOGIN_VERIFICATION:

                IdentifyNumPage identifyNumPage = new IdentifyNumPage();
                if(isFirstRegister){
                    identifyNumPage.setPhone(phone, code, formatedPhone,TitleType.VERIFICATION_REGISTER);
                    isFirstRegister = false;
                }else {
                    identifyNumPage.setPhone(phone, code, formatedPhone, TitleType.VERIFICATION_LOGIN);
                }
                identifyNumPage.showForResult(this.activity, (Intent) null, this);
                break;
            default:
                break;
        }
    }

    private void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void showWaitingDialog(int message) {

    }

    @Override
    public void dismissWaitingDialog() {

    }

    @Override
    public void showToast(String toastText) {
        Toast.makeText(MyRegisterPage.this.activity, toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTips(String toastText) {

    }

    @Override
    public void LoginSuccess(String platform) {
        HashMap<String, Object> res = new HashMap();
//        res.put("res", platform));
//        res.put("page", Integer.valueOf(2));
//        res.put("phone", data);
//        setResult(res);
        onActivityResult(0, LoginConfig.RESPONSE_LOGIN_SUCCESS,null);
        sendResult();
        finish();
    }
}

package com.elf.appstore.account.ui;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elf.appstore.R;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by liuzuocai on 18-3-24.
 */

public class PasswordNumPage extends FakeActivity implements View.OnClickListener, VerificationCodeView.InputCompleteListener {
    private static final int RETRY_INTERVAL = 5;
    private static final int MIN_REQUEST_VOICE_VERIFY_INTERVAL = 1000;
    public static final String TEMP_CODE = "1319972";
    private String phone;
    private String code;
    private String formatedPhone;
    private int time = RETRY_INTERVAL;
    private EventHandler handler;
    private TextView tvTitle;
    private TextView tvPhone;
    private TextView tvIdentifyNotify;
    private TextView tvUnreceiveIdentify;
    private ImageView ivClear;
    private Button mNext;
    private VerificationCodeView mPasswordEditText;
    private int showDialogType = 1;
    private long lastRequestVVTime;

    private TextView mTips;

    private String mPassword;

    private MyRegisterPage.TitleType mTitleType = MyRegisterPage.TitleType.PWD_LOGIN;

    public PasswordNumPage() {
    }

    public void setPhone(String phone, String code, String formatedPhone, MyRegisterPage.TitleType titleType, String inputContent) {
        Log.d("liuzuo","PasswordNumPage:"+Log.getStackTraceString(new Exception()));
        this.phone = phone;
        this.code = code;
        this.formatedPhone = formatedPhone;
        this.mTitleType = titleType;
        this.mPassword = inputContent;
    }

    public void onCreate() {

        initView();
        initListener();
        initType();
    }

    private void initType() {
        if (mTitleType == MyRegisterPage.TitleType.PWD_SETUP) {
            tvTitle.setText(R.string.login_pwd_title_set);
            mTips.setVisibility(View.INVISIBLE);
        } else if (mTitleType == MyRegisterPage.TitleType.PWD_INPUT_AGAIN) {
            tvTitle.setText(R.string.login_pwd_title_verity);
            mTips.setVisibility(View.INVISIBLE);
        }
    }


    private void initView() {
        PasswordNumPageLayout page = new PasswordNumPageLayout(this.activity);
        LinearLayout layout = page.getLayout();
        if (layout != null) {
            this.activity.setContentView(layout);
            this.activity.findViewById(ResHelper.getIdRes(this.activity, "ll_back")).setOnClickListener(this);
            tvTitle = this.activity.findViewById(R.id.login_pwd_title);
            tvPhone = this.activity.findViewById(R.id.login_pwd_title_content);
            mPasswordEditText = this.activity.findViewById(R.id.login_pwd_et);
            mNext = this.activity.findViewById(R.id.btn_submit);
            mTips = this.activity.findViewById(R.id.login_pwd_tips);
        }
    }

    private void initListener() {
        mNext.setOnClickListener(this);
        mTips.setOnClickListener(this);
        mPasswordEditText.setInputCompleteListener(this);

    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public boolean onFinish() {

        return super.onFinish();
    }


    public void onClick(View v) {
        int id = v.getId();
        int idLlBack = ResHelper.getIdRes(this.activity, "ll_back");
        if (id == idLlBack) {
            finish();
        } else if (v == mPasswordEditText) {

        } else if (v == mNext) {
            String inputContent = mPasswordEditText.getInputContent();
            if (verifyPasswordDigit(inputContent)) {

                afterSubmit(inputContent.trim());
            }
        }else if(v==mTips){
            IdentifyNumPage identifyNumPage = new IdentifyNumPage();
            identifyNumPage.setPhone(phone, code, formatedPhone, MyRegisterPage.TitleType.VERIFICATION_REGISTER);
            identifyNumPage.showForResult(this.activity, (Intent) null, this);
            HashMap<String, Object> res = new HashMap();
            res.put("notStartUI", Boolean.valueOf(true));
            res.put("page", Integer.valueOf(3));
            setResult(res);
            sendResult();
            SMSSDK.getVerificationCode(code, phone.trim());
        }


    }


    private boolean verifyPasswordDigit(String pwd) {
        return pwd != null && pwd.trim().length() == 6;
    }


    @Override
    public void inputComplete() {
        String inputContent = mPasswordEditText.getInputContent();
        if (verifyPasswordDigit(inputContent)) {
            afterSubmit(inputContent.trim());
        }
    }

    @Override
    public void deleteContent() {

    }

    private void afterSubmit(String inputContent) {
        if (mTitleType == MyRegisterPage.TitleType.PWD_LOGIN) {
//            HashMap<String, Object> res = new HashMap();
//            res.put("isPass", Boolean.valueOf(true));
//            res.put("page", Integer.valueOf(4));
//            setResult(res);
//            sendResult();
//            finish();
            showDialog();
        } else if (mTitleType == MyRegisterPage.TitleType.PWD_SETUP) {
            PasswordNumPage page = new PasswordNumPage();
            page.setPhone(phone, code, formatedPhone, MyRegisterPage.TitleType.PWD_INPUT_AGAIN, inputContent);
            page.showForResult(this.activity, (Intent) null, this);

        } else if (mTitleType == MyRegisterPage.TitleType.PWD_INPUT_AGAIN) {
            if (mPassword != null && mPassword.equals(inputContent)) {
//                HashMap<String, Object> res = new HashMap();
//                res.put("isPass", Boolean.valueOf(true));
//                res.put("page", Integer.valueOf(4));
//                setResult(res);
//                sendResult();
//                finish();
                showDialog();
            } else {
                Toast.makeText(this.activity, "两次密码不一致", Toast.LENGTH_SHORT).show();
            }
        } else if (mTitleType == MyRegisterPage.TitleType.PWD_RESET) {

        }

    }

    @Override
    public void onResult(HashMap<String, Object> data) {
        if(data!=null) {
            Object pageNum = data.get("page");
            if (data != null && pageNum instanceof Integer) {
                int page = (Integer) pageNum;

                if (page == 4||page==3) {
                    Object pass = data.get("isPass");
                    if (pass instanceof Boolean && (boolean) pass) {
                        Toast.makeText(this.activity, "设置并登录成功", Toast.LENGTH_SHORT).show();
                        HashMap<String, Object> res = new HashMap();
                        res.put("isPass", Boolean.valueOf(true));
                        res.put("page", Integer.valueOf(3));
                        setResult(res);
                        sendResult();
                        finish();
                    }
                }
            }
        }
    }
    public void showDialog(){
        int resId = ResHelper.getStyleRes(this.activity, "login_dialog");
        if(resId > 0) {
            final Dialog dialog = new Dialog(this.getContext(),resId);
            View layout = this.activity.getLayoutInflater().inflate(R.layout.login_success_dialog, null);
            if(layout != null) {
                dialog.setContentView(layout);
                TextView tv = dialog.findViewById(R.id.login_success_dialog_title);
                if (mTitleType == MyRegisterPage.TitleType.PWD_INPUT_AGAIN){
                    tv.setText(R.string.login_register_dialog_pwd_success);
                }
                (dialog.findViewById(ResHelper.getIdRes(this.activity, "login_success_dialog_ok"))).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (mTitleType == MyRegisterPage.TitleType.PWD_LOGIN) {
                            HashMap<String, Object> res = new HashMap();
                            res.put("isPass", Boolean.valueOf(true));
                            res.put("page", Integer.valueOf(4));
                            setResult(res);
                            sendResult();
                            finish();
                        } else if (mTitleType == MyRegisterPage.TitleType.PWD_INPUT_AGAIN) {
                            HashMap<String, Object> res = new HashMap();
                            res.put("isPass", Boolean.valueOf(true));
                            res.put("page", Integer.valueOf(4));
                            setResult(res);
                            sendResult();
                            finish();
                        }
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        }
    }
}


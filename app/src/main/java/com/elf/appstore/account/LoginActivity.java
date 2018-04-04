package com.elf.appstore.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elf.appstore.R;

public abstract class LoginActivity extends Activity implements View.OnClickListener ,ILoginView{

    private static final int MSG_ACTION_CCALLBACK = 0;
    private ImageView ivWxLogin;
    private ImageView ivQqLogin;
    private ImageView ivBlog;
    private ImageView ivFacebook;
    private ImageView ivTwitter;
    private EditText  mEdPhone;
    private EditText  mEdPassword;
    private TextView  mLoginTips;
    private CountDownView  mCountDownView;
    private LoginSubmit  mLoginSubmit;
    private LoginPresenter mPresenter;
    private ProgressDialog progressDialog;

    public static final int WECHATTYPE = 1;
    public static final int QQTYPE = 2;
    public static final int SINATYPE = 3;
    public static final int FACEBOOKTYPE = 4;
    public static final int TWITTERTYPE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initPresenter();
        initListener();
        initData();
    }

    private void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }


    public void initView() {
        ivWxLogin = (ImageView) findViewById(R.id.iv_wx_login);
        ivQqLogin = (ImageView) findViewById(R.id.iv_qq_login);
        ivBlog = (ImageView) findViewById(R.id.iv_blog);
        ivFacebook = (ImageView) findViewById(R.id.iv_facebook);
        ivTwitter = (ImageView) findViewById(R.id.iv_twitter);
        mEdPhone = findViewById(R.id.login_phone_num);
        mEdPassword = findViewById(R.id.login_verification_num);
        mLoginTips = findViewById(R.id.login_tips);
        mCountDownView = findViewById(R.id.login_send_verification);
        mLoginSubmit = findViewById(R.id.login_bottom);
    }


    public void initListener() {
        ivWxLogin.setOnClickListener(this);
        ivQqLogin.setOnClickListener(this);
        ivBlog.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        mCountDownView.setUserEdit(mEdPhone);
        mCountDownView.setCaptchaListener(new LoginLoader.CaptchaListener() {
            @Override
            public void onPre() {

            }

            @Override
            public void onComplete(String phoneOrEmail) {

            }
        },mPresenter);
        setDataByType(LoginLoader.SubmitType.LOGIN_FAST, mLoginSubmit);
        mLoginSubmit.setSubmitListener(new LoginLoader.SubmitListener() {
            @Override
            public void onComplete(String... strings) {
                if(strings!=null&&strings.length>1){
                    mPresenter.submitCode(strings[0],strings[1]);
                }

            }
        },mPresenter);

    }


    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            default:
                break;
        }

    }


    //显示dialog
    public void showProgressDialog(int message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message==-1?"":getString(message));
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    //隐藏dialog
    public void hideProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void showWaitingDialog(int message) {
        showProgressDialog(message);

    }

    @Override
    public void dismissWaitingDialog() {
        hideProgressDialog();

    }

    @Override
    public void showToast(String toastText) {
        Toast.makeText(LoginActivity.this, toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTips(String messageText) {
    if(mLoginTips!=null){
        mLoginTips.setVisibility(View.VISIBLE);
        mLoginTips.setText(messageText);
    }
    }

    private void setDataByType(LoginLoader.SubmitType submitType, LoginSubmit loginSubmit) {
        if (loginSubmit == null) return;
        if (submitType == null) return;
        loginSubmit.setSubmitType(submitType);
        switch (submitType) {
/*            case LOGIN:
                loginSubmit.setData(mEdEmail, mEdPassword);
                return;
            case REGISTER:
                loginSubmit.setData(mEdEmail, mEdCode, mEdPassword);
                return;*/
            case LOGIN_FAST:
            case PASSWORD_FORGET:
                loginSubmit.setData(mEdPhone, mEdPassword);
                return;
            case NORMAL:
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(mPresenter!=null){
            mPresenter.unregisterAll();
        }
        super.onDestroy();
    }
}

package com.elf.appstore.account;


public class VerifyUserLogImpl extends VerifyUser {

    private String mErrorMessage;
    public VerifyUserLogImpl( boolean mIsShowToast) {
        isShowToast = mIsShowToast;
    }

    @Override
    public boolean verifyPhoneOrEmail(String phoneOrEmail) {
        boolean isFlag = StringUtils.isValidEmail(phoneOrEmail)
                || StringUtils.isValidPhoneNumber(phoneOrEmail);
        if (!isFlag && isShowToast) {
            //ToastUtils.show(mContext, "账户格式错误");
            mErrorMessage = "账户格式错误";
        }
        return isFlag;
    }

    @Override
    public boolean verifyPassword(String password) {
        boolean isFlag = StringUtils.isValidPassword(password);
        if (!isFlag && isShowToast) {
            //ToastUtils.show(mContext, "密码格式错误");
            mErrorMessage = "密码格式错误";
        }
        return isFlag;
    }

    @Override
    public boolean verifyCaptcha(String captcha) {
        boolean isFlag = StringUtils.isValidCaptcha(captcha);
        if (!isFlag && isShowToast) {
            //ToastUtils.show(mContext, "验证码格式错误");
            mErrorMessage = "验证码格式错误";
        }
        return isFlag;
    }

    @Override
    public boolean checkUser(LoginLoader.SubmitType inputType, String... strings) {
        return super.checkUser(inputType, strings);
    }

    @Override
    public  void setIsShowToast(boolean misShowToast) {
        isShowToast = misShowToast;
    }
    public String getErrorMessage() {
        return mErrorMessage;
    }
}

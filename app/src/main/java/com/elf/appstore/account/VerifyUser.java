package com.elf.appstore.account;

public abstract class VerifyUser {
    boolean isShowToast = false;
    private String mErrorMessage;

    protected abstract boolean verifyPhoneOrEmail(String phoneOrEmail);
    protected abstract boolean verifyPassword(String password);
    protected abstract boolean verifyCaptcha(String captcha);
    public abstract void setIsShowToast(boolean isShowToast);

    public boolean checkUser(LoginLoader.SubmitType inputType, String... strings) {
        if (strings == null) return false;
        switch (inputType) {
            case LOGIN:
                return verifyPhoneOrEmail(strings[0])
                        && verifyPassword(strings[1]);
            case REGISTER:
                return verifyPhoneOrEmail(strings[0])
                        && verifyCaptcha(strings[1])
                        && verifyPassword(strings[2]);
            case LOGIN_FAST:
            case PASSWORD_FORGET:
                mErrorMessage = "输入的信息有误";
                return verifyPhoneOrEmail(strings[0])
                        && verifyCaptcha(strings[1]);
            case NORMAL:
            default:
                break;
        }
        return false;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }
}


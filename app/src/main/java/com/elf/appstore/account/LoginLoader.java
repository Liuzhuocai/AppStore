package com.elf.appstore.account;

public class LoginLoader {

    public interface CaptchaListener {
        void onPre();
        void onComplete(String phoneOrEmail);
    }

    public interface SubmitListener {
        void onComplete(String... strings);
    }

    public enum SubmitType {
        LOGIN, REGISTER, LOGIN_FAST, PASSWORD_FORGET, NORMAL
    }

}

package com.elf.appstore.account;

/**
 * Created by liuzuocai on 18-3-20.
 */

public interface ILoginView {
    void showWaitingDialog(int message);
    void dismissWaitingDialog();

    void showToast(String toastText);
    void showTips(String toastText);
}

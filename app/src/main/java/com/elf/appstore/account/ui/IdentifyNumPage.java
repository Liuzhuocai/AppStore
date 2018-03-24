package com.elf.appstore.account.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elf.appstore.R;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.SMSReceiver;
import cn.smssdk.gui.layout.BackVerifyDialogLayout;
import cn.smssdk.gui.layout.SendMsgDialogLayout;
import cn.smssdk.utils.SMSLog;

/**
 * Created by liuzuocai on 18-3-24.
 */

public class IdentifyNumPage extends FakeActivity implements View.OnClickListener, TextWatcher {
    private static final int RETRY_INTERVAL = 5;
    private static final int MIN_REQUEST_VOICE_VERIFY_INTERVAL = 1000;
    public static final String TEMP_CODE = "1319972";
    private String phone;
    private String code;
    private String formatedPhone;
    private int time = RETRY_INTERVAL;
    private EventHandler handler;
    private Dialog pd;
    private EditText etIdentifyNum;
    private TextView tvTitle;
    private TextView tvPhone;
    private TextView tvIdentifyNotify;
    private TextView tvUnreceiveIdentify;
    private ImageView ivClear;
    private Button btnSubmit;
    private Button btnSounds;
    private BroadcastReceiver smsReceiver;
    private int showDialogType = 1;
    private long lastRequestVVTime;

    public IdentifyNumPage() {
    }

    public void setPhone(String phone, String code, String formatedPhone) {
        this.phone = phone;
        this.code = code;
        this.formatedPhone = formatedPhone;
    }

    public void onCreate() {
        IdentifyNumPageLayout page = new IdentifyNumPageLayout(this.activity);
        LinearLayout layout = page.getLayout();
        if(layout != null) {
            this.activity.setContentView(layout);
            this.activity.findViewById(ResHelper.getIdRes(this.activity, "ll_back")).setOnClickListener(this);
            this.btnSubmit = (Button)this.activity.findViewById(ResHelper.getIdRes(this.activity, "btn_submit"));
            this.btnSubmit.setOnClickListener(this);
            this.btnSubmit.setEnabled(false);
            this.tvTitle = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_title"));
            int resId = ResHelper.getStringRes(this.activity, "smssdk_write_identify_code");
            if(resId > 0) {
                this.tvTitle.setText(resId);
            }

            this.etIdentifyNum = (EditText)this.activity.findViewById(ResHelper.getIdRes(this.activity, "et_put_identify"));
            this.etIdentifyNum.addTextChangedListener(this);
            this.tvIdentifyNotify = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_identify_notify"));
            resId = ResHelper.getStringRes(this.activity, "smssdk_send_mobile_detail");
            String unReceive;
            if(resId > 0) {
                unReceive = this.getContext().getString(resId);
                this.tvIdentifyNotify.setText(Html.fromHtml(unReceive));
            }

            this.tvPhone = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_phone"));
            this.tvPhone.setText(this.formatedPhone);
            this.tvUnreceiveIdentify = (TextView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "tv_unreceive_identify"));
            resId = ResHelper.getStringRes(this.activity, "smssdk_receive_msg");
            if(resId > 0) {
                unReceive = this.getContext().getString(resId, new Object[]{Integer.valueOf(this.time)});
                this.tvUnreceiveIdentify.setText(Html.fromHtml(unReceive));
            }

            this.tvUnreceiveIdentify.setOnClickListener(this);
            this.tvUnreceiveIdentify.setEnabled(false);
            this.ivClear = (ImageView)this.activity.findViewById(ResHelper.getIdRes(this.activity, "iv_clear"));
            this.ivClear.setOnClickListener(this);
            this.btnSounds = (Button)this.findViewById(ResHelper.getIdRes(this.activity, "btn_sounds"));
            this.btnSounds.setOnClickListener(this);
            this.handler = new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    if(event == 3) {
                        afterSubmit(result, data);
                    } else if(event == 2) {
                        afterGet(result, data);
                    } else if(event == 8) {
                        afterGetVoice(result, data);
                    }

                }
            };
            SMSSDK.registerEventHandler(this.handler);
            this.countDown();
        }

        try {
            if(DeviceHelper.getInstance(this.activity).checkPermission("android.permission.RECEIVE_SMS")) {
                this.smsReceiver = new SMSReceiver(new SMSSDK.VerifyCodeReadListener() {
                    public void onReadVerifyCode(final String verifyCode) {
                        runOnUIThread(new Runnable() {
                            public void run() {
                                etIdentifyNum.setText(verifyCode);
                            }
                        });
                    }
                });
                this.activity.registerReceiver(this.smsReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
            }
        } catch (Throwable var5) {
            var5.printStackTrace();
            this.smsReceiver = null;
        }

    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public boolean onFinish() {
        SMSSDK.unregisterEventHandler(this.handler);
        if(this.smsReceiver != null) {
            try {
                this.activity.unregisterReceiver(this.smsReceiver);
            } catch (Throwable var2) {
                var2.printStackTrace();
            }
        }

        return super.onFinish();
    }

    private void countDown() {
        this.runOnUIThread(new Runnable() {
            public void run() {
                time--;
                int resId;
                String unReceive;
                if(time == 0) {
                    resId = ResHelper.getStringRes(activity, "smssdk_unreceive_identify_code");
                    if(resId > 0) {
                        unReceive = getContext().getString(resId, new Object[]{Integer.valueOf(time)});
                        tvUnreceiveIdentify.setText(Html.fromHtml(unReceive));
                    }

                    tvUnreceiveIdentify.setEnabled(true);
                    //btnSounds.setVisibility(View.VISIBLE);
                    btnSounds.setText(R.string.login_verification_again);
                    time = RETRY_INTERVAL;
                } else {
                    resId = ResHelper.getStringRes(activity, "smssdk_receive_msg");
                    if(resId > 0) {
                        unReceive = getContext().getString(resId, new Object[]{Integer.valueOf(time)});
                        tvUnreceiveIdentify.setText(Html.fromHtml(unReceive));
                        resId = ResHelper.getStringRes(activity, "smssdk_receive_msg");
                        resId = R.string.login_verification_timer;
                        btnSounds.setText(Html.fromHtml(getContext().getString(resId, new Object[]{Integer.valueOf(time)})));
                    }

                    tvUnreceiveIdentify.setEnabled(false);
                    runOnUIThread(this, 1000L);
                }

            }
        }, 1000L);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int resId;
        if(s.length() > 0) {
            this.btnSubmit.setEnabled(true);
            this.ivClear.setVisibility(View.VISIBLE);
            resId = ResHelper.getBitmapRes(this.activity, "smssdk_btn_enable");
            if(resId > 0) {
                this.btnSubmit.setBackgroundResource(resId);
            }
        } else {
            this.btnSubmit.setEnabled(false);
            this.ivClear.setVisibility(View.GONE);
            resId = ResHelper.getBitmapRes(this.activity, "smssdk_btn_disenable");
            if(resId > 0) {
                this.btnSubmit.setBackgroundResource(resId);
            }
        }

    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
    }

    public void onClick(View v) {
        int id = v.getId();
        int idLlBack = ResHelper.getIdRes(this.activity, "ll_back");
        int idBtnSubmit = ResHelper.getIdRes(this.activity, "btn_submit");
        int idTvUnreceiveIdentify = ResHelper.getIdRes(this.activity, "tv_unreceive_identify");
        int idIvClear = ResHelper.getIdRes(this.activity, "iv_clear");
        int idBtnSounds = ResHelper.getIdRes(this.activity, "btn_sounds");
        if(id == idLlBack) {
            this.runOnUIThread(new Runnable() {
                public void run() {
                    showNotifyDialog();
                }
            });
        } else if(id == idBtnSubmit) {
            String verificationCode = this.etIdentifyNum.getText().toString().trim();
            if(!TextUtils.isEmpty(this.code)) {
                if(this.pd != null && this.pd.isShowing()) {
                    this.pd.dismiss();
                }

                this.pd = CommonDialog.ProgressDialog(this.activity);
                if(this.pd != null) {
                    this.pd.show();
                }

                SMSSDK.submitVerificationCode(this.code, this.phone, verificationCode);
            } else {
                int resId = ResHelper.getStringRes(this.activity, "smssdk_write_identify_code");
                if(resId > 0) {
                    Toast.makeText(this.getContext(), resId, Toast.LENGTH_SHORT).show();
                }
            }
        } else if(id == idTvUnreceiveIdentify) {
            this.showDialogType = 1;
            this.showDialog(this.showDialogType);
        } else if(id == idIvClear) {
            this.etIdentifyNum.getText().clear();
        } else if(id == idBtnSounds) {
            long time = System.currentTimeMillis();
            if(time - this.lastRequestVVTime > 1000L) {
                this.lastRequestVVTime = time;
//                this.showDialogType = 2;
//                this.showDialog(this.showDialogType);
                gerVerificationAgain();
            }
        }

    }

    private void gerVerificationAgain() {
        SMSSDK.getVerificationCode(code, phone.trim(), (OnSendMessageHandler)null);
        countDown();
    }

    private void showDialog(int type) {
        int resId;
        final Dialog dialog;
        if(type == 1) {
            resId = ResHelper.getStyleRes(this.activity, "CommonDialog");
            if(resId > 0) {
                dialog = new Dialog(this.getContext(), resId);
                TextView tv = new TextView(this.getContext());
                if(type == 1) {
                    resId = ResHelper.getStringRes(this.activity, "smssdk_resend_identify_code");
                } else {
                    resId = ResHelper.getStringRes(this.activity, "smssdk_send_sounds_identify_code");
                }

                if(resId > 0) {
                    tv.setText(resId);
                }

                tv.setTextSize(2, 18.0F);
                resId = ResHelper.getColorRes(this.activity, "smssdk_white");
                if(resId > 0) {
                    tv.setTextColor(this.getContext().getResources().getColor(resId));
                }

                int dp10 = ResHelper.dipToPx(this.getContext(), 10);
                tv.setPadding(dp10, dp10, dp10, dp10);
                dialog.setContentView(tv);
                tv.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        tvUnreceiveIdentify.setEnabled(false);
                        if(pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }

                        pd = CommonDialog.ProgressDialog(activity);
                        if(pd != null) {
                            pd.show();
                        }

                        SMSSDK.getVerificationCode(code, phone.trim(), "1319972", (OnSendMessageHandler)null);
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        tvUnreceiveIdentify.setEnabled(true);
                    }
                });
                dialog.show();
            }
        } else if(type == 2) {
            resId = ResHelper.getStyleRes(this.activity, "CommonDialog");
            if(resId > 0) {
                dialog = new Dialog(this.getContext(), resId);
                LinearLayout layout = SendMsgDialogLayout.create(this.activity);
                if(layout != null) {
                    dialog.setContentView(layout);
                    TextView tvTitle = (TextView)dialog.findViewById(ResHelper.getIdRes(this.activity, "tv_dialog_title"));
                    resId = ResHelper.getStringRes(this.activity, "smssdk_make_sure_send_sounds");
                    if(resId > 0) {
                        tvTitle.setText(resId);
                    }

                    TextView tv = (TextView)dialog.findViewById(ResHelper.getIdRes(this.activity, "tv_dialog_hint"));
                    resId = ResHelper.getStringRes(this.activity, "smssdk_send_sounds_identify_code");
                    if(resId > 0) {
                        String text = this.getContext().getString(resId);
                        tv.setText(text);
                    }

                    ((Button)dialog.findViewById(ResHelper.getIdRes(this.activity, "btn_dialog_ok"))).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                            SMSSDK.getVoiceVerifyCode(code, phone);
                        }
                    });
                    ((Button)dialog.findViewById(ResHelper.getIdRes(this.activity, "btn_dialog_cancel"))).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                }
            }
        }

    }

    private void afterSubmit(final int result, final Object data) {
        this.runOnUIThread(new Runnable() {
            public void run() {
                if(pd != null && pd.isShowing()) {
                    pd.dismiss();
                }

                if(result == -1) {
                    HashMap<String, Object> res = new HashMap();
                    res.put("res", Boolean.valueOf(true));
                    res.put("page", Integer.valueOf(2));
                    res.put("phone", data);
                    setResult(res);
                    finish();
                } else {
                    ((Throwable)data).printStackTrace();
                    String message = ((Throwable)data).getMessage();
                    int resId = 0;

                    try {
                        JSONObject json = new JSONObject(message);
                        int status = json.getInt("status");
                        resId = ResHelper.getStringRes(activity, "smssdk_error_detail_" + status);
                    } catch (JSONException var5) {
                        var5.printStackTrace();
                    }

                    if(resId == 0) {
                        resId = ResHelper.getStringRes(activity, "smssdk_virificaition_code_wrong");
                    }

                    if(resId > 0) {
                        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void afterGet(final int result, final Object data) {
        this.runOnUIThread(new Runnable() {
            public void run() {
                if(pd != null && pd.isShowing()) {
                    pd.dismiss();
                }

                if(result == -1) {
                    int resId = ResHelper.getStringRes(activity, "smssdk_virificaition_code_sent");
                    if(resId > 0) {
                        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
                    }

                    resId = ResHelper.getStringRes(activity, "smssdk_receive_msg");
                    if(resId > 0) {
                        String unReceive = getContext().getString(resId, new Object[]{Integer.valueOf(time)});
                        tvUnreceiveIdentify.setText(Html.fromHtml(unReceive));
                    }

                    //btnSounds.setVisibility(View.GONE);
                    time = 60;
                    countDown();
                } else {
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable)data;
                    int status = 0;

                    try {
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");
                        status = object.optInt("status");
                        if(!TextUtils.isEmpty(des)) {
                            Toast.makeText(activity, des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException var5) {
                        SMSLog.getInstance().w(var5);
                    }

                    int resIdxx;
                    if(status >= 400) {
                        resIdxx = ResHelper.getStringRes(activity, "smssdk_error_desc_" + status);
                    } else {
                        resIdxx = ResHelper.getStringRes(activity, "smssdk_network_error");
                    }

                    if(resIdxx > 0) {
                        Toast.makeText(activity, resIdxx, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void afterGetVoice(final int result, final Object data) {
        this.runOnUIThread(new Runnable() {
            public void run() {
                if(pd != null && pd.isShowing()) {
                    pd.dismiss();
                }

                if(result == -1) {
                    int resId = ResHelper.getStringRes(activity, "smssdk_send_sounds_success");
                    if(resId > 0) {
                        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
                    }

                    //btnSounds.setVisibility(View.GONE);
                } else {
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable)data;
                    int status = 0;

                    try {
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");
                        status = object.optInt("status");
                        if(!TextUtils.isEmpty(des)) {
                            Toast.makeText(activity, des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException var5) {
                        SMSLog.getInstance().w(var5);
                    }

                    int resIdxx;
                    if(status >= 400) {
                        resIdxx = ResHelper.getStringRes(activity, "smssdk_error_desc_" + status);
                    } else {
                        resIdxx = ResHelper.getStringRes(activity, "smssdk_network_error");
                    }

                    if(resIdxx > 0) {
                        Toast.makeText(activity, resIdxx, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void showNotifyDialog() {
        int resId = ResHelper.getStyleRes(this.activity, "CommonDialog");
        if(resId > 0) {
            final Dialog dialog = new Dialog(this.getContext(), resId);
            LinearLayout layout = BackVerifyDialogLayout.create(this.activity);
            if(layout != null) {
                dialog.setContentView(layout);
                resId = ResHelper.getIdRes(this.activity, "tv_dialog_hint");
                TextView tv = (TextView)dialog.findViewById(resId);
                resId = ResHelper.getStringRes(this.activity, "smssdk_close_identify_page_dialog");
                if(resId > 0) {
                    tv.setText(resId);
                }

                resId = ResHelper.getIdRes(this.activity, "btn_dialog_ok");
                Button waitBtn = (Button)dialog.findViewById(resId);
                resId = ResHelper.getStringRes(this.activity, "smssdk_wait");
                if(resId > 0) {
                    waitBtn.setText(resId);
                }

                waitBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                resId = ResHelper.getIdRes(this.activity, "btn_dialog_cancel");
                Button backBtn = (Button)dialog.findViewById(resId);
                resId = ResHelper.getStringRes(this.activity, "smssdk_back");
                if(resId > 0) {
                    backBtn.setText(resId);
                }

                backBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        }

    }

    public boolean onKeyEvent(int keyCode, KeyEvent event) {
        if(keyCode == 4 && event.getAction() == 0) {
            this.runOnUIThread(new Runnable() {
                public void run() {
                    //showNotifyDialog();
                }
            });
            return true;
        } else {
            return false;
        }
    }
}


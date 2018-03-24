package com.elf.appstore.account.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elf.appstore.R;
import com.mob.tools.utils.ResHelper;

import cn.smssdk.gui.layout.SizeHelper;

/**
 * Created by liuzuocai on 18-3-24.
 */

public class IdentifyNumPageLayout extends BasePageLayout {
    public IdentifyNumPageLayout(Context c) {
        super(c, false);
    }

    protected void onCreateContent(LinearLayout parent) {
        LinearLayout wrapperLayout = new LinearLayout(this.context);
        LinearLayout.LayoutParams wrapperParams = new LinearLayout.LayoutParams(-1, -1);
        wrapperLayout.setLayoutParams(wrapperParams);
        wrapperLayout.setBackgroundColor(-1);
        wrapperLayout.setOrientation(LinearLayout.VERTICAL);
        wrapperLayout.setPadding(SizeHelper.fromPxWidth(26), 0, SizeHelper.fromPxWidth(26), 0);
        parent.addView(wrapperLayout);
        TextView identifyNotify = new TextView(this.context);
        identifyNotify.setId(ResHelper.getIdRes(this.context, "tv_identify_notify"));
        LinearLayout.LayoutParams identifyNotifyParams = new LinearLayout.LayoutParams(-1, -2);
        identifyNotifyParams.topMargin = SizeHelper.fromPxWidth(32);
        identifyNotify.setGravity(17);
        identifyNotify.setLayoutParams(identifyNotifyParams);
        int resid = ResHelper.getStringRes(this.context, "smssdk_make_sure_mobile_detail");
        identifyNotify.setText(resid);
        identifyNotify.setTextColor(-6710887);
        identifyNotify.setTextSize(0, (float)SizeHelper.fromPxWidth(24));
        wrapperLayout.addView(identifyNotify);
        TextView phone = new TextView(this.context);
        phone.setId(ResHelper.getIdRes(this.context, "tv_phone"));
        LinearLayout.LayoutParams phoneParams = new LinearLayout.LayoutParams(-1, -2);
        phoneParams.topMargin = SizeHelper.fromPxWidth(50);
        phone.setGravity(17);
        phone.setLayoutParams(identifyNotifyParams);
        phone.setTextColor(-13290187);
        phone.setTextSize(0, (float)SizeHelper.fromPxWidth(26));
        phone.setTypeface(Typeface.DEFAULT_BOLD);
        wrapperLayout.addView(phone);
        RelativeLayout inputBg = new RelativeLayout(this.context);
        LinearLayout.LayoutParams inputBgParams = new LinearLayout.LayoutParams(-1, SizeHelper.fromPxWidth(72));
        inputBgParams.topMargin = SizeHelper.fromPxWidth(38);
        inputBg.setLayoutParams(inputBgParams);
        resid = ResHelper.getBitmapRes(this.context, "smssdk_input_bg_focus");
        inputBg.setBackgroundResource(resid);
        wrapperLayout.addView(inputBg);
        android.widget.RelativeLayout.LayoutParams putIdentifyParams = new android.widget.RelativeLayout.LayoutParams(-1, -1);
        putIdentifyParams.leftMargin = SizeHelper.fromPxWidth(18);
        EditText putIdentify = new EditText(this.context);
        putIdentify.setLayoutParams(putIdentifyParams);
        putIdentify.setId(ResHelper.getIdRes(this.context, "et_put_identify"));
        resid = ResHelper.getStringRes(this.context, "smssdk_write_identify_code");
        putIdentify.setHint(resid);
        putIdentify.setBackgroundColor(-1);
        putIdentify.setSingleLine(true);
        putIdentify.setTextSize(0, (float)SizeHelper.fromPxWidth(24));
        putIdentify.setInputType(2);
        inputBg.addView(putIdentify);
        android.widget.RelativeLayout.LayoutParams clearImageParams = new android.widget.RelativeLayout.LayoutParams(SizeHelper.fromPxWidth(34), SizeHelper.fromPxWidth(34));
        clearImageParams.addRule(15);
        clearImageParams.addRule(7, ResHelper.getIdRes(this.context, "et_put_identify"));
        ImageView clearImage = new ImageView(this.context);
        clearImage.setLayoutParams(clearImageParams);
        clearImage.setId(ResHelper.getIdRes(this.context, "iv_clear"));
        resid = ResHelper.getBitmapRes(this.context, "smssdk_clear_search");
        clearImage.setImageResource(resid);
        clearImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        clearImage.setVisibility(View.GONE);
        inputBg.addView(clearImage);
        Button soundBtn = new Button(this.context);
        soundBtn.setId(ResHelper.getIdRes(this.context, "btn_sounds"));
        android.widget.RelativeLayout.LayoutParams soundParams = new android.widget.RelativeLayout.LayoutParams(-2, SizeHelper.fromPxWidth(54));
        soundParams.addRule(12);
        soundParams.addRule(7, ResHelper.getIdRes(this.context, "et_put_identify"));
        soundBtn.setLayoutParams(soundParams);
        resid = ResHelper.getBitmapRes(this.context, "smssdk_btn_disenable");
        soundBtn.setBackgroundResource(resid);
        resid = ResHelper.getStringRes(this.context, "smssdk_send_sounds");
        soundBtn.setText(R.string.login_verification_again);
        soundBtn.setTextColor(this.context.getResources().getColor(ResHelper.getColorRes(this.context, "smssdk_white")));
        soundBtn.setTextSize(0, (float) SizeHelper.fromPxWidth(20));
        int padding = SizeHelper.fromPxWidth(18);
        soundBtn.setPadding(padding, 0, padding, 0);
        //soundBtn.setVisibility(View.GONE);
        inputBg.addView(soundBtn);
        TextView unreceive = new TextView(this.context);
        unreceive.setId(ResHelper.getIdRes(this.context, "tv_unreceive_identify"));
        LinearLayout.LayoutParams unreceiveParams = new LinearLayout.LayoutParams(-1, -2);
        unreceiveParams.setMargins(0, SizeHelper.fromPxWidth(34), 0, SizeHelper.fromPxWidth(30));
        unreceive.setLayoutParams(unreceiveParams);
        unreceive.setGravity(17);
        resid = ResHelper.getStringRes(this.context, "smssdk_receive_msg");
        unreceive.setText(resid);
        unreceive.setTextColor(-10724260);
        unreceive.setTextSize(0, (float)SizeHelper.fromPxWidth(22));
        wrapperLayout.addView(unreceive);
        Button submitBtn = new Button(this.context);
        submitBtn.setId(ResHelper.getIdRes(this.context, "btn_submit"));
        LinearLayout.LayoutParams submitParams = new LinearLayout.LayoutParams(-1, SizeHelper.fromPxWidth(72));
        submitBtn.setLayoutParams(submitParams);
        resid = ResHelper.getBitmapRes(this.context, "smssdk_btn_disenable");
        submitBtn.setBackgroundResource(resid);
        resid = ResHelper.getStringRes(this.context, "smssdk_next");
        submitBtn.setText(resid);
        submitBtn.setTextColor(-1);
        submitBtn.setTextSize(0, (float)SizeHelper.fromPxWidth(24));
        submitBtn.setPadding(SizeHelper.fromPxWidth(10), 0, SizeHelper.fromPxWidth(10), 0);
        wrapperLayout.addView(submitBtn);
    }
}

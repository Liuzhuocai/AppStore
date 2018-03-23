package com.elf.appstore.account.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elf.appstore.R;
import com.mob.tools.utils.ResHelper;

import cn.smssdk.gui.layout.SizeHelper;
/**
 * Created by liuzuocai on 18-3-23.
 */

public class RegisterPageLayout extends BasePageLayout {
        public RegisterPageLayout(Context context) {
            super(context, false);
        }

        protected void onCreateContent(LinearLayout parent) {
            SizeHelper.prepare(this.context);
            LinearLayout rlCountry = new LinearLayout(this.context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, SizeHelper.fromPxWidth(96));
            params.setMargins(SizeHelper.fromPx(26), SizeHelper.fromPxWidth(32), SizeHelper.fromPxWidth(26), 0);
            rlCountry.setLayoutParams(params);
            rlCountry.setId(ResHelper.getIdRes(this.context, "rl_country"));
            TextView tv = new TextView(this.context);
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(-2, -2);
            tvParams.gravity = 16;
            tv.setLayoutParams(tvParams);
            tv.setPadding(SizeHelper.fromPxWidth(14), 0, SizeHelper.fromPxWidth(14), 0);
            int resid = ResHelper.getStringRes(this.context, "smssdk_country");
            tv.setText(resid);
            tv.setTextColor(-16777216);
            tv.setTextSize(0, (float)SizeHelper.fromPxWidth(25));
            rlCountry.addView(tv);
            TextView tvCountry = new TextView(this.context);
            tvCountry.setId(ResHelper.getIdRes(this.context, "tv_country"));
            LinearLayout.LayoutParams tvCountryParams = new LinearLayout.LayoutParams(-2, -2);
            tvCountryParams.gravity = 16;
            tvCountryParams.weight = 1.0F;
            tvCountryParams.rightMargin = SizeHelper.fromPxWidth(14);
            tvCountry.setLayoutParams(tvCountryParams);
            tvCountry.setGravity(5);
            tvCountry.setPadding(SizeHelper.fromPxWidth(14), 0, SizeHelper.fromPxWidth(14), 0);
            tvCountry.setTextColor(-12206054);
            tvCountry.setTextSize(0, (float)SizeHelper.fromPxWidth(25));
            rlCountry.addView(tvCountry);
            parent.addView(rlCountry);
            View line = new View(this.context);
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(-2, SizeHelper.fromPxWidth(1));
            lineParams.leftMargin = SizeHelper.fromPxWidth(26);
            lineParams.rightMargin = SizeHelper.fromPxWidth(26);
            line.setLayoutParams(lineParams);
            line.setBackgroundColor(this.context.getResources().getColor(ResHelper.getColorRes(this.context, "smssdk_gray_press")));
            parent.addView(line);
            LinearLayout phoneLayout = new LinearLayout(this.context);
            LinearLayout.LayoutParams phoneParams = new LinearLayout.LayoutParams(-1, SizeHelper.fromPxWidth(70));
            phoneParams.setMargins(SizeHelper.fromPxWidth(26), SizeHelper.fromPxWidth(30), SizeHelper.fromPxWidth(26), 0);
            phoneLayout.setLayoutParams(phoneParams);
            TextView countryNum = new TextView(this.context);
            countryNum.setId(ResHelper.getIdRes(this.context, "tv_country_num"));
            LinearLayout.LayoutParams countryNumParams = new LinearLayout.LayoutParams(SizeHelper.fromPxWidth(104), -1);
            countryNum.setLayoutParams(countryNumParams);
            countryNum.setGravity(17);
            countryNum.setTextColor(-13290187);
            countryNum.setTextSize(0, (float)SizeHelper.fromPxWidth(25));
            resid = ResHelper.getBitmapRes(this.context, "smssdk_input_bg_focus");
            countryNum.setBackgroundResource(resid);
            phoneLayout.addView(countryNum);
            LinearLayout wrapperLayout = new LinearLayout(this.context);
            LinearLayout.LayoutParams wrapperParams = new LinearLayout.LayoutParams(0, -1);
            wrapperParams.weight = 1.0F;
            wrapperLayout.setLayoutParams(wrapperParams);
            resid = ResHelper.getBitmapRes(this.context, "smssdk_input_bg_special_focus");
            wrapperLayout.setBackgroundResource(resid);
            EditText writePhone = new EditText(this.context);
            writePhone.setId(ResHelper.getIdRes(this.context, "et_write_phone"));
            LinearLayout.LayoutParams writePhoneParams = new LinearLayout.LayoutParams(0, -2);
            writePhoneParams.gravity = 16;
            writePhoneParams.setMargins(SizeHelper.fromPxWidth(10), 0, SizeHelper.fromPxWidth(10), 0);
            writePhoneParams.weight = 1.0F;
            writePhone.setLayoutParams(writePhoneParams);
            writePhone.setBackgroundDrawable((Drawable)null);
            resid = ResHelper.getStringRes(this.context, "smssdk_write_mobile_phone");
            writePhone.setHint(resid);
            writePhone.setInputType(3);
            writePhone.setTextColor(-13290187);
            writePhone.setTextSize(0, (float) SizeHelper.fromPxWidth(25));
            wrapperLayout.addView(writePhone);
            ImageView image = new ImageView(this.context);
            image.setId(ResHelper.getIdRes(this.context, "iv_clear"));
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(SizeHelper.fromPxWidth(24), SizeHelper.fromPxWidth(24));
            imageParams.gravity = 16;
            imageParams.rightMargin = SizeHelper.fromPxWidth(20);
            image.setLayoutParams(imageParams);
            resid = ResHelper.getBitmapRes(this.context, "smssdk_clear_search");
            image.setBackgroundResource(resid);
            image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            image.setVisibility(View.GONE);
            wrapperLayout.addView(image);
            phoneLayout.addView(wrapperLayout);
            parent.addView(phoneLayout);
            Button nextBtn = new Button(this.context);
            nextBtn.setId(ResHelper.getIdRes(this.context, "btn_next"));
            LinearLayout.LayoutParams nextParams = new LinearLayout.LayoutParams(-1, SizeHelper.fromPxWidth(72));
            nextParams.setMargins(SizeHelper.fromPxWidth(26), SizeHelper.fromPxWidth(36), SizeHelper.fromPxWidth(26), 0);
            nextBtn.setLayoutParams(nextParams);
            resid = ResHelper.getBitmapRes(this.context, "smssdk_btn_disenable");
            nextBtn.setBackgroundResource(resid);
            resid = ResHelper.getStringRes(this.context, "smssdk_next");
            nextBtn.setText(resid);
            nextBtn.setTextColor(-1);
            nextBtn.setTextSize(0, (float)SizeHelper.fromPxWidth(25));
            nextBtn.setPadding(0, 0, 0, 0);
            parent.addView(nextBtn);



            View view = mLayoutInflater.inflate(R.layout.login_third_container, null);
            parent.addView(view);
        }
}

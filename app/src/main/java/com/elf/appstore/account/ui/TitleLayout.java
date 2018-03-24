package com.elf.appstore.account.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.tools.utils.ResHelper;

import cn.smssdk.gui.layout.SizeHelper;

/**
 * Created by liuzuocai on 18-3-23.
 */

public class TitleLayout {
    static final int IN_HEIGHT = 74;
    static final int LINE_HEIGHT = 2;

    public TitleLayout() {
    }

    static LinearLayout create(Context context, boolean isSearch) {
        SizeHelper.prepare(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        LinearLayout titleLayout = new LinearLayout(context);
        titleLayout.setLayoutParams(params);
        titleLayout.setOrientation(LinearLayout.VERTICAL);
        View topLine = new View(context);
        LinearLayout.LayoutParams topLineParams = new LinearLayout.LayoutParams(-1, SizeHelper.fromPxWidth(1));
        topLine.setLayoutParams(topLineParams);
        topLine.setBackgroundColor(-12236213);
        titleLayout.addView(topLine);
        if(isSearch) {
            createSearch(titleLayout, context);
        } else {
            createNormal(titleLayout, context);
        }

        View bottomLine = new View(context);
        LinearLayout.LayoutParams bottomLineParams = new LinearLayout.LayoutParams(-1, SizeHelper.fromPxWidth(2));
        bottomLine.setLayoutParams(bottomLineParams);
        bottomLine.setBackgroundColor(-15066083);
        titleLayout.addView(bottomLine);
        return titleLayout;
    }

    private static void createNormal(LinearLayout titleLayout, Context context) {
        int height = SizeHelper.fromPx(74);
        LinearLayout.LayoutParams inParams = new LinearLayout.LayoutParams(-1, height);
        LinearLayout inLayout = new LinearLayout(context);
        inLayout.setLayoutParams(inParams);
        inLayout.setBackgroundColor(-13617865);
        LinearLayout.LayoutParams backParams = new LinearLayout.LayoutParams(-2, -1);
        LinearLayout backLayout = new LinearLayout(context);
        backLayout.setLayoutParams(backParams);
        backLayout.setId(ResHelper.getIdRes(context, "ll_back"));
        backLayout.setPadding(SizeHelper.fromPx(14), 0, SizeHelper.fromPx(26), 0);
        LinearLayout.LayoutParams arrowParams = new LinearLayout.LayoutParams(SizeHelper.fromPx(15), SizeHelper.fromPx(25));
        arrowParams.gravity = 16;
        arrowParams.rightMargin = SizeHelper.fromPx(14);
        ImageView backArrow = new ImageView(context);
        backArrow.setLayoutParams(arrowParams);
        int res = ResHelper.getBitmapRes(context, "smssdk_back_arrow");
        backArrow.setBackgroundResource(res);
        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(SizeHelper.fromPx(30), SizeHelper.fromPx(44));
        logoParams.gravity = 16;
        logoParams.rightMargin = SizeHelper.fromPx(14);
        ImageView backLogo = new ImageView(context);
        backLogo.setLayoutParams(logoParams);
//        res = ResHelper.getBitmapRes(context, "smssdk_sharesdk_icon");
//        backLogo.setBackgroundResource(res);
        backLayout.addView(backArrow);
        backLayout.addView(backLogo);
        inLayout.addView(backLayout);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(-2, -2);
        titleParams.gravity = 16;
        TextView title = new TextView(context);
        title.setLayoutParams(titleParams);
        title.setId(ResHelper.getIdRes(context, "tv_title"));
        title.setTextColor(-3158065);
        title.setTextSize(0, (float)SizeHelper.fromPx(32));
        inLayout.addView(title);
        titleLayout.addView(inLayout);
    }

    private static void createSearch(LinearLayout titleLayout, Context context) {
        int height = SizeHelper.fromPx(74);
        LinearLayout.LayoutParams inParams = new LinearLayout.LayoutParams(-1, height);
        LinearLayout inLayout = new LinearLayout(context);
        inLayout.setLayoutParams(inParams);
        inLayout.setBackgroundColor(-13617865);
        inLayout.setBaselineAligned(false);
        LinearLayout.LayoutParams backParams = new LinearLayout.LayoutParams(-2, -1);
        LinearLayout backLayout = new LinearLayout(context);
        backLayout.setLayoutParams(backParams);
        backLayout.setId(ResHelper.getIdRes(context, "ll_back"));
        backLayout.setPadding(SizeHelper.fromPx(14), 0, SizeHelper.fromPx(26), 0);
        LinearLayout.LayoutParams arrowParams = new LinearLayout.LayoutParams(SizeHelper.fromPx(15), SizeHelper.fromPx(25));
        arrowParams.gravity = 16;
        arrowParams.rightMargin = SizeHelper.fromPxWidth(14);
        ImageView backArrow = new ImageView(context);
        backArrow.setLayoutParams(arrowParams);
        int res = ResHelper.getBitmapRes(context, "smssdk_back_arrow");
        backArrow.setBackgroundResource(res);
        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(SizeHelper.fromPx(30), SizeHelper.fromPx(44));
        logoParams.gravity = 16;
        logoParams.rightMargin = SizeHelper.fromPx(14);
        ImageView backLogo = new ImageView(context);
        backLogo.setLayoutParams(logoParams);
        res = ResHelper.getBitmapRes(context, "smssdk_sharesdk_icon");
        backLogo.setBackgroundResource(res);
        backLayout.addView(backArrow);
        backLayout.addView(backLogo);
        inLayout.addView(backLayout);
        LinearLayout.LayoutParams innerTitleParams = new LinearLayout.LayoutParams(-2, -1, 1.0F);
        LinearLayout innerTitleLayout = new LinearLayout(context);
        innerTitleLayout.setLayoutParams(innerTitleParams);
        innerTitleLayout.setId(ResHelper.getIdRes(context, "llTitle"));
        inLayout.addView(innerTitleLayout);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, -2, 1.0F);
        titleParams.gravity = 16;
        TextView title = new TextView(context);
        title.setLayoutParams(titleParams);
        title.setId(ResHelper.getIdRes(context, "tv_title"));
        res = ResHelper.getStringRes(context, "smssdk_choose_country");
        title.setText(res);
        title.setTextColor(-3158065);
        title.setTextSize(0, (float)SizeHelper.fromPx(32));
        innerTitleLayout.addView(title);
        LinearLayout.LayoutParams searchImageParams = new LinearLayout.LayoutParams(SizeHelper.fromPx(70), -2);
        searchImageParams.gravity = 16;
        ImageView searchImage = new ImageView(context);
        searchImage.setLayoutParams(searchImageParams);
        searchImage.setId(ResHelper.getIdRes(context, "ivSearch"));
        res = ResHelper.getBitmapRes(context, "smssdk_search_icon");
        searchImage.setImageResource(res);
        searchImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        searchImage.setPadding(SizeHelper.fromPx(15), 0, SizeHelper.fromPx(15), 0);
        innerTitleLayout.addView(searchImage);
        LinearLayout.LayoutParams innerSearchParams = new LinearLayout.LayoutParams(-2, -2, 1.0F);
        innerSearchParams.gravity = 16;
        innerSearchParams.rightMargin = SizeHelper.fromPx(18);
        LinearLayout innerSearchLayout = new LinearLayout(context);
        innerSearchLayout.setLayoutParams(innerSearchParams);
        innerSearchLayout.setId(ResHelper.getIdRes(context, "llSearch"));
        res = ResHelper.getBitmapRes(context, "smssdk_input_bg_focus");
        innerSearchLayout.setBackgroundResource(res);
        innerSearchLayout.setPadding(SizeHelper.fromPx(14), 0, SizeHelper.fromPx(14), 0);
        innerSearchLayout.setVisibility(View.GONE);
        inLayout.addView(innerSearchLayout);
        LinearLayout.LayoutParams searchIconParams = new LinearLayout.LayoutParams(SizeHelper.fromPx(36), SizeHelper.fromPx(36));
        searchIconParams.gravity = 16;
        searchIconParams.rightMargin = SizeHelper.fromPx(8);
        ImageView searchIcon = new ImageView(context);
        searchIcon.setLayoutParams(searchIconParams);
        res = ResHelper.getBitmapRes(context, "smssdk_search_icon");
        searchIcon.setImageResource(res);
        searchIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        innerSearchLayout.addView(searchIcon);
        LinearLayout.LayoutParams identifyParams = new LinearLayout.LayoutParams(0, -2, 1.0F);
        identifyParams.gravity = 16;
        EditText identify = new EditText(context);
        identify.setLayoutParams(identifyParams);
        identify.setId(ResHelper.getIdRes(context, "et_put_identify"));
        res = ResHelper.getStringRes(context, "smssdk_search");
        identify.setHint(res);
        identify.setTextColor(-1);
        identify.setBackgroundDrawable((Drawable)null);
        identify.setSingleLine(true);
        innerSearchLayout.addView(identify);
        LinearLayout.LayoutParams clearParams = new LinearLayout.LayoutParams(SizeHelper.fromPx(30), SizeHelper.fromPx(30));
        clearParams.gravity = 16;
        clearParams.rightMargin = SizeHelper.fromPxWidth(5);
        ImageView clear = new ImageView(context);
        clear.setLayoutParams(clearParams);
        clear.setId(ResHelper.getIdRes(context, "iv_clear"));
        res = ResHelper.getBitmapRes(context, "smssdk_clear_search");
        clear.setImageResource(res);
        clear.setScaleType(ImageView.ScaleType.FIT_CENTER);
        innerSearchLayout.addView(clear);
        titleLayout.addView(inLayout);
    }
}

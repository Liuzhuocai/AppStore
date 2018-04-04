package com.elf.appstore.widget.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elf.appstore.R;
import com.elf.appstore.widget.ui.base.BaseActivity;

public class ActivityMyInformation extends BaseActivity {
    private CircleImageView informationMeIconIv;
    private EditText informationMeNameEt;
    private LinearLayout informationMeSex;
    private TextView informationMeSexText;
    private LinearLayout informationMeBirthday;
    private TextView informationMeBirthdayText;
    private LinearLayout informationMeLocation;
    private TextView informationMeLocationText;
    private LinearLayout informationMeWexin;
    private TextView informationMeWexinText;
    private LinearLayout informationMeFacebook;
    private TextView informationMeFacebookText;
    private LinearLayout informationMeTwtter;
    private TextView informationMeTwtterText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        setCenterTitle(R.string.information_me);
        initView();
        initListener();
    }

    private void initListener() {
        informationMeIconIv.setOnClickListener(this);

    }

    private void initView() {
        informationMeIconIv = (CircleImageView) findViewById(R.id.information_me_icon_iv);
        informationMeNameEt = (EditText) findViewById(R.id.information_me_name_et);
        informationMeSex = (LinearLayout) findViewById(R.id.information_me_sex);
        informationMeSexText = (TextView) findViewById(R.id.information_me_sex_text);
        informationMeBirthday = (LinearLayout) findViewById(R.id.information_me_birthday);
        informationMeBirthdayText = (TextView) findViewById(R.id.information_me_birthday_text);
        informationMeLocation = (LinearLayout) findViewById(R.id.information_me_location);
        informationMeLocationText = (TextView) findViewById(R.id.information_me_location_text);
        informationMeWexin = (LinearLayout) findViewById(R.id.information_me_wexin);
        informationMeWexinText = (TextView) findViewById(R.id.information_me_wexin_text);
        informationMeFacebook = (LinearLayout) findViewById(R.id.information_me_facebook);
        informationMeFacebookText = (TextView) findViewById(R.id.information_me_facebook_text);
        informationMeTwtter = (LinearLayout) findViewById(R.id.information_me_twtter);
        informationMeTwtterText = (TextView) findViewById(R.id.information_me_twtter_text);
    }
    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.information_me_icon_iv:
               getPicture();
               break;
           default:
               break;

       }
    }

    public void getPicture() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, 1);
    }
}

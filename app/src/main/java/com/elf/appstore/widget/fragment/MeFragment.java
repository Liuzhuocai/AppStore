package com.elf.appstore.widget.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elf.appstore.R;
import com.elf.appstore.account.LoginConfig;
import com.elf.appstore.account.ui.MyRegisterPage;
import com.elf.appstore.widget.base.BaseMainFragment;
import com.elf.appstore.widget.ui.ActivityMyInformation;
import com.elf.appstore.widget.ui.ActivitySetting;

/**
 * Created by liuzuocai on 18-3-28.
 */

public class MeFragment extends BaseMainFragment implements View.OnClickListener {
    private LinearLayout flFirstContainer;
    private ImageView fragmentMeUserImage;
    private TextView fragmentMeUserNickname;
    //private ImageView fragmentMeEnterInformation;
    private TextView fragmentMeAppUpdate;
    private TextView fragmentMePackageManager;
    private TextView fragmentMeSettings;
    private TextView fragmentMeAbout;
    private TextView fragmentMeAboutUpdate;
    private LoginType mStatus = LoginType.LOGIN_OUT;
    public   enum LoginType{
        LOGIN_IN , LOGIN_OUT ,//登录界面类型
    }

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        fragmentMeUserNickname.setOnClickListener(this);
        fragmentMeSettings.setOnClickListener(this);
    }

    public void initView(View view) {
        flFirstContainer = view.findViewById(R.id.fl_first_container);
        fragmentMeUserImage =  view.findViewById(R.id.fragment_me_user_image);
        fragmentMeUserNickname =  view.findViewById(R.id.fragment_me_user_nickname);
        //fragmentMeEnterInformation =  view.findViewById(R.id.fragment_me_enter_information);
        fragmentMeAppUpdate =  view.findViewById(R.id.fragment_me_app_update);
        fragmentMePackageManager =  view.findViewById(R.id.fragment_me_package_manager);
        fragmentMeSettings =  view.findViewById(R.id.fragment_me_settings);
        fragmentMeAbout =  view.findViewById(R.id.fragment_me_about);
        fragmentMeAboutUpdate =  view.findViewById(R.id.fragment_me_about_update);

    }
    public void goThirdPartyLogin() {
/*        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);*/
        MyRegisterPage page = new MyRegisterPage();
       /* page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                } else{
                    Log.d("liuzuo","data="+data.toString());
                    // TODO 处理错误的结果
                }
            }
        });*/
        page.show(this.getContext());
    }

    @Override
    public void onClick(View view) {
        if(view==fragmentMeUserNickname){
            goThirdPartyLogin();
            //enterMyInformation();
            onclickProtect(fragmentMeUserNickname);
        }else if(view == fragmentMeSettings){
            enterSettings();
        }
    }

    private void enterMyInformation() {
        Intent intent = new Intent(_mActivity,ActivityMyInformation.class);
        startActivityForResult(intent,1);
    }
    private void enterSettings() {
        Intent intent = new Intent(_mActivity,ActivitySetting.class);
        startActivityForResult(intent,1);
    }
    private void onclickProtect(View view) {
        if(view!=null){
            view.setEnabled(false);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                }
            },1000);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== LoginConfig.RESPONSE_LOGIN_SUCCESS){

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

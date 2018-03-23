package com.elf.appstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.elf.appstore.account.ui.MyRegisterPage;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goThirdPartyLogin(View view) {
/*        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);*/
        MyRegisterPage page = new MyRegisterPage();
        page.setRegisterCallback(new EventHandler() {
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
        });
        page.show(this);
    }
}

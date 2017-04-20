package com.example.administrator.vchate.UI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.administrator.vchate.R;
import com.example.administrator.vchate.UI.BaseActivity;
import com.hyphenate.chat.EMClient;

/**
 * Created by Administrator on 2017/4/20.
 */

public class SplashActivity extends BaseActivity {
    Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {
            if (EMClient.getInstance().isLoggedInBefore()) {
                intent2Main();
            } else {
                intent2Login();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

handler.sendEmptyMessageDelayed(1,3000);
    }
}

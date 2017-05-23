package com.vx.vchat.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.vx.vchat.R;


public class SplashAcitvity extends BaseActivity {
    /**
     * 接受跳转的消息。
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            initNext();
        }
    };

    /**
     * 跳转到下一个页面。
     */
    private void initNext() {
        //判断之前是否登陆过。
        if (EMClient.getInstance().isLoggedInBefore()) {
            intenToMain();
        } else {
            intentLogin();
        }
        SplashAcitvity.this.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //发送演示消息。
        handler.sendEmptyMessageDelayed(1, 3000);
        findViewById(R.id.splash_img)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.removeMessages(1);

                        intenToMain();
                    }
                });
    }

    @Override
    public void onBackPressed() {

    }
}

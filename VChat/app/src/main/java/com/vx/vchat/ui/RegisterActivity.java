package com.vx.vchat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.vx.vchat.R;

public class RegisterActivity extends BaseActivity {
    private EditText user_et;
    private EditText pass_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        user_et = (EditText) findViewById(R.id.et_user);
        pass_et = (EditText) findViewById(R.id.et_pass);
    }
    public void register(View view){
        String user = user_et.getText().toString();
        String pass = pass_et.getText().toString();
        System.out.println("账号："+user+"--------密码："+pass);
        try {
            EMClient.getInstance().createAccount(user,pass);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
}

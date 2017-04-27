package com.vx.vchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hyphenate.chat.EMClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.e(TAG,"show Log");
findViewById(R.id.user_name).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        EMClient.getInstance().logout(true);
    }
});
    }
}

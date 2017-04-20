package com.example.administrator.vchate.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.vchate.MainActivity;

/**
 * Created by Administrator on 2017/4/20.
 */

public class BaseActivity extends AppCompatActivity {
    public void showtoast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
    public void intent2Login(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void intent2Main(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}

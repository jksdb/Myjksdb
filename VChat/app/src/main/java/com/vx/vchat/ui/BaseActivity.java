package com.vx.vchat.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
public  void intentLogin(){
    Intent intent =new Intent(this,LoginActivity.class);
    startActivity(intent);
}
    public  void intenToMain(){
        Intent ins =new Intent(this,MainActivity.class);
        startActivity(ins);
    }








}

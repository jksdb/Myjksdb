package com.vx.vchat.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.vx.vchat.R;
import com.vx.vchat.adapter.MessageAdapter;

import java.util.ArrayList;

/**
 * Created by asus on 2017/5/8.
 */

public class MessageActivity extends BaseActivity {
    private String name;
    private String caogao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        getDataFromIntent();
        //获取会话对象
        EMConversation conversation = EMClient.getInstance().chatManager()
                .getConversation(name);
        //获取此会话所有信息
        ArrayList<EMMessage> allMessages = (ArrayList<EMMessage>) conversation.getAllMessages();

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.message_recyclerview);
        MessageAdapter messageAdpter=new MessageAdapter(allMessages,this);
        LinearLayoutManager lim=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lim);
        recyclerView.setAdapter(messageAdpter);



    }

    private void getDataFromIntent() {
        name = getIntent().getStringExtra("name");
        caogao = getIntent().getStringExtra("caogao");

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_item_1:
                Toast.makeText(this, "menu_item_1", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    //返回键的方法   只限于返回键
    public void onBackPressed() {

        setResult(RESULT_OK);

        //作用 关闭页面
        super.onBackPressed();

    }
}

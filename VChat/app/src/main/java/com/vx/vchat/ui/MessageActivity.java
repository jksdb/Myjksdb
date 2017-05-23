package com.vx.vchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.vx.vchat.Messager.MessageManager;
import com.vx.vchat.R;
import com.vx.vchat.adapter.MessageAdapter;
import com.vx.vchat.fragment.ImageSelectFrament;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/5/8.
 */

public class MessageActivity extends BaseActivity implements EMMessageListener, View.OnClickListener {
    private String tex;
    private String name;
    private String caogao;
    private ArrayList<EMMessage> allMessages;
    private MessageAdapter messageAdpter;
    private Button send, btn1, btn2, btn3, btn4;
    private EditText edittext;
    private RecyclerView recyclerView;
    private int lastPostion = 0;
    ImageSelectFrament imageSelectFrament;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

//从 intent中获取 数据并设置
        getDataFromIntent();
        //消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        //获取消息记录
        allMessages = getList();
        initListView();
        initClick();

        setTextString();


    }


    private void setTextString() {
        edittext.setText(caogao);
        edittext.setSelection(edittext.getText().length());
    }


    private void initClick() {
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        send = (Button) findViewById(R.id.button5);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        send.setOnClickListener(this);
        edittext = (EditText) findViewById(R.id.message_ed);
        imageSelectFrament = new ImageSelectFrament();

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom > oldBottom && oldBottom != 0) {

                } else if (bottom < oldBottom) {
                    recyclerView.scrollToPosition(lastPostion);
                }
                Log.e("Left/top/right/bottom", left + "/" + top + "/" + right + "/" + bottom + "/");
                Log.e("oldL/oldt/oldr/oldb", oldLeft + "/" + oldTop + "/" + oldRight + "/" + oldBottom + "/");
            }
        });

        //添加   文本改变监听  处理文本改变后要做的逻辑
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tex = s.toString();
            }
        });

    }

    private void initListView() {

        recyclerView = (RecyclerView) findViewById(R.id.message_recyclerview);
        messageAdpter = new MessageAdapter(allMessages, this);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        //recyclerView  滑动到底部   推荐使用
        lim.setStackFromEnd(true);
        recyclerView.setLayoutManager(lim);
        recyclerView.setAdapter(messageAdpter);
//        lastPostion=allMessages.size() - 1;
//        recyclerView.scrollToPosition(lastPostion);
    }

    private ArrayList<EMMessage> getList() {
        //获取会话对象
        EMConversation conversation = EMClient.getInstance().chatManager()
                .getConversation(name);
        //标记所有消息已读
        conversation.markAllMessagesAsRead();
        //从绘话对象中获取所有消息
        ArrayList<EMMessage> msgList = (ArrayList<EMMessage>) conversation.getAllMessages();
        //如果  回话中的消息个数为1 尝试从数据库中获取 19 条
        // 要求  ：： 最终  消息列表中要有20条消息
        if (msgList.size() == 1) {
            conversation.loadMoreMsgFromDB(msgList.get(0).getMsgId(), 19);
        }


        //获取此会话所有信息
        return (ArrayList<EMMessage>) conversation.getAllMessages();

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

        Intent data = new Intent();
        data.putExtra("user", name);
        data.putExtra("tex", tex);
//设置  activity 关闭时  返回的数据
        setResult(RESULT_OK, data);

        //作用 关闭页面
        super.onBackPressed();

    }


    @Override
    public void onMessageReceived(final List<EMMessage> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                allMessages.addAll(list);
                messageAdpter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    private void createTxt(String string) {
        EMMessage msg = EMMessage.createTxtSendMessage(string, name);
        sendMsg(msg);
    }

    public void createImage(String path) {

        EMMessage msg = EMMessage.createImageSendMessage(path, false, name);
        sendMsg(msg);
}

    private void sendMsg(EMMessage msg) {

        msg.setChatType(EMMessage.ChatType.Chat);
        EMClient.getInstance().chatManager().sendMessage(msg);
        tex = "";
        allMessages.add(msg);
        messageAdpter.notifyDataSetChanged();

        MessageManager
                .getInstance()
                .getMessageList()
                .refChatList();


    }

    @Override
    protected void onDestroy() {
        EMClient.getInstance().chatManager().removeMessageListener(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.button5:
                String text = edittext.getText().toString();
                createTxt(text);
                edittext.setText("");
                break;
            case R.id.button1:

                openImageSelect();
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
        }
    }

    private void openImageSelect() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //判断是否添加
        if (imageSelectFrament.isAdded()) {
            //如果添加则删除
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(imageSelectFrament);
            transaction.commit();
//清空该 fragment 的返回栈
            fragmentManager.popBackStackImmediate();
        } else {
            //如果没有添加  添加并替换
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.message_bottom_fragmnt_select, imageSelectFrament);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


}

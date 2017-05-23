package com.vx.vchat.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.vx.vchat.Messager.MessageManager;
import com.vx.vchat.R;
import com.vx.vchat.adapter.MyAdapter;
import com.vx.vchat.callback.MessageList;
import com.vx.vchat.callback.RecyclItemClick;
import com.vx.vchat.mode.DeffStringBean;
import com.vx.vchat.ui.MessageActivity;
import com.vx.vchat.until.Sputil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class ChatListFragment extends BaseFragment implements MessageList {
    private RecyclerView recyclerview;
    private ArrayList<EMConversation> list = new ArrayList<>();
    private Map<String, EMConversation> conversation;
    private MyAdapter myAdapter;
    private Map<String, String> testStr = new HashMap<>();
    private Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, null);


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        getChatList();

        initView(view);
        MessageManager.getInstance().setMessageList(this);


        getDeffFromSp();

    }

    private void getDeffFromSp() {
        String json = Sputil.getChatDeff(getActivity());
        Type types = new TypeToken<ArrayList<DeffStringBean>>() {
        }.getType();
        ArrayList<DeffStringBean> jsonArr = gson.fromJson(json, types);
        if (jsonArr != null) {
            for (DeffStringBean d : jsonArr) {
                testStr.put(d.getKey(), d.getDeff());
            }
        }
    }

    private void getChatList() {
        //获取会话列表
        conversation = EMClient.getInstance()
                .chatManager()
                .getAllConversations();
//将Map转成list
        Collection<EMConversation> values = conversation.values();
        Iterator<EMConversation> it = values.iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
    }


    private void initView(View view) {
//        for (int i = 0; i < 50; i++) {
//            list.add("i=" + i);
//        }


//        view.findViewById(R.id.chat_btn_test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////              list.add(1,"add item");
//
//                myAdapter.notifyItemChanged(1);
//            }
//        });
//==========================

        recyclerview = (RecyclerView) view.findViewById(R.id.fragment_recycler);


        myAdapter = new MyAdapter(getActivity(), list);

//线
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        llm.setOrientation(LinearLayoutManager.HORIZONTAL);//横向


//网格
        //GridLayoutManager GLM = new GridLayoutManager(getActivity(), 3);

//瀑布流
        //   StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);


        recyclerview.setItemAnimator(new DefaultItemAnimator());//动画

        recyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);

                Paint paint = new Paint();
                paint.setColor(getResources().getColor(R.color.colorbai));
                //左内边距  宽度不是坐标
                int Left = parent.getPaddingLeft();
                //测量的宽度 右
                int Right = parent.getMeasuredWidth() - parent.getPaddingRight();
//便利控件
                int size = parent.getChildCount();
                for (int i = 0; i < size; i++) {

                    View child = parent.getChildAt(i);
                    //上下坐标
                    int top = child.getBottom();
                    int bottom = top + 3;
                    c.drawRect(Left, top, Right, bottom, paint);
                }


            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 3);
            }
        });


        myAdapter.setRecyclItemClicke(new RecyclItemClick() {
            @Override
            public void onItemClick(int index) {
                Toast.makeText(getActivity(), "onItemClick", Toast.LENGTH_SHORT).show();

                intent2Message(list.get(index).getUserName());

            }

            @Override
            public boolean onItemLongClick(int index) {
                Toast.makeText(getActivity(), "coItemLongClick", Toast.LENGTH_SHORT).show();


                return true;
            }
        });

        recyclerview.setLayoutManager(llm);
        recyclerview.setAdapter(myAdapter);
        //动画
//        myAdapter.notifyItemInserted();
//        myAdapter.notifyItemRemoved();
//        myAdapter.notifyItemMoved();
    }


    public void intent2Message(String name) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        intent.putExtra("name", name);

        intent.putExtra("caogao", testStr.get(name));

        startActivityForResult(intent, 101);

    }

    /**
     * @param requestCode 请求码 用于区别此次返回是哪一个请求
     * @param resultCode  返回码  用于标记此次是陈宫还是失败
     * @param data        返回数据
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//判断返回码是否成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //根据不同返回码做不同逻辑
                case 101:
                    getDeff(data);

                    cunchucaogao();


                    break;
            }
        }
    }

    private void cunchucaogao() {
        //   存储草稿
//拔草稿  map集合 转成 List集合
        List<DeffStringBean> deffs = new ArrayList<>();
        //拿到 map集合中的 key集合
        Iterator<String> keys = testStr.keySet().iterator();
//遍历 key集合
        while (keys.hasNext()) {
            //拿到每一次 key的值
            String keysC = keys.next();
           //通过key 的值 从map集合中取到相应的value
            //并设置给实体类
            DeffStringBean deffStringBean = new DeffStringBean();
            deffStringBean.setDeff(testStr.get(keysC));
            deffStringBean.setKey(keysC);
            //吧  实体类添加到list集合中
            deffs.add(deffStringBean);


        }
        //把实体类集合转成  接送字符串
        String json = new Gson().toJson(deffs);
        //吧  json 字符串  存到  sp
        Sputil.setChatDeff(getActivity(), json);
    }

    private void getDeff(Intent data) {
        String key = data.getStringExtra("user");
        String valey = data.getStringExtra("tex");
        if (TextUtils.isEmpty(valey)) {
            testStr.remove(key);
        } else {
            testStr.put(key, valey);
        }
        myAdapter.setTextStr(testStr);
    }

    public void resetList() {
        list.clear();
        getChatList();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void refChatList() {
        resetList();
    }
}

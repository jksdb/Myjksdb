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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.vx.vchat.R;
import com.vx.vchat.adapter.MyAdapter;
import com.vx.vchat.callback.RecyclItemClick;
import com.vx.vchat.ui.MessageActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


public class ChatListFragment extends BaseFragment {
    private RecyclerView recyclerview;
    private ArrayList<EMConversation> list = new ArrayList<>();
    private Map<String, EMConversation> conversation;
    private MyAdapter myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, null);


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
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
        initView(view);
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
                    int bottom = top + 10;
                    c.drawRect(Left, top, Right, bottom, paint);
                }


            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 10);
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
        intent.putExtra("caogao", "");

        startActivityForResult(intent, 101);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getActivity(), "requestCode" + requestCode + "resultCode=" + resultCode, Toast.LENGTH_SHORT).show();


    }

}

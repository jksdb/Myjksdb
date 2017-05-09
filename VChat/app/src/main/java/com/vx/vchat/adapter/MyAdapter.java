package com.vx.vchat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.method.DateTimeKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.vx.vchat.R;
import com.vx.vchat.callback.RecyclItemClick;

import java.util.ArrayList;
import java.util.Date;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<EMConversation> list;
    private RecyclItemClick recyclItemClicke;

    public void setRecyclItemClicke(RecyclItemClick recyclItemClicke) {
        this.recyclItemClicke = recyclItemClicke;
    }

    public MyAdapter(Context context, ArrayList<EMConversation> list) {
        this.context = context;
        this.list = list;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, string, time, unread;
        private ImageView iv;
        private View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.item_chat_lay);
            name = (TextView) itemView.findViewById(R.id.item_name);
            string = (TextView) itemView.findViewById(R.id.item_String);
            time = (TextView) itemView.findViewById(R.id.item_time);
            unread = (TextView) itemView.findViewById(R.id.item_unread);
            iv = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //获取数据源对象
        EMConversation mData = list.get(position);
        //获得对话类型
        mData.getType();
        //获得最后一条消息
        EMMessage lastMessage = mData.getLastMessage();
        //活动消息类型
        EMMessage.Type msgType = lastMessage.getType();

        switch (msgType) {
            case TXT:
                EMTextMessageBody txtMsg = (EMTextMessageBody) lastMessage.getBody();
                //消息内容
                txtMsg.getMessage();
                holder.string.setText(txtMsg.getMessage());
                break;
            case IMAGE:

                holder.string.setText("图片");

                break;
            case VIDEO:

                holder.string.setText("视频");

                break;


        }


        //获得 消息时间
        long msgTime = lastMessage.getMsgTime();
        Date date = new Date(msgTime);

        holder.time.setText(date + "");

        //获得未读消息数
        int unreadMsgCount = mData.getUnreadMsgCount();
        // 会话名
        String userName = mData.getUserName();
        holder.name.setText(userName);
        if (unreadMsgCount > 99) {
            holder.unread.setText("99+");
        } else {
            holder.unread.setText(unreadMsgCount + "");
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclItemClicke.onItemClick(position);
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return recyclItemClicke.onItemLongClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();

    }
}

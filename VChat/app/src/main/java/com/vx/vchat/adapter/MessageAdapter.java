package com.vx.vchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 import com.hyphenate.chat.EMMessage;
import com.vx.vchat.R;

import java.util.List;

/**
 * Created by asus on 2017/5/9.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<EMMessage> list;
    private Context context;

    public MessageAdapter(List<EMMessage> list, Context context) {
        this.list = list;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView message_text, message_mag;

        public MyViewHolder(View itemView) {
            super(itemView);
            message_text = (TextView) itemView.findViewById(R.id.message_text);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {
        EMMessage msg = list.get(position);
        holder.message_text.setText(msg.getBody().toString());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

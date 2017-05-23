package com.vx.vchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.vx.vchat.R;

import java.util.List;

import static com.hyphenate.chat.EMMessage.Type.IMAGE;
import static com.hyphenate.chat.EMMessage.Type.TXT;

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
        private TextView time, left_content, right_content;
        private ImageView left_header, right_header, msgStatus;
        private View Leftlay, rightlay;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            left_content = (TextView) itemView.findViewById(R.id.item_message_left_content);
            right_content = (TextView) itemView.findViewById(R.id.item_message_right_content);
            left_header = (ImageView) itemView.findViewById(R.id.item_message_left_header_img);
            right_header = (ImageView) itemView.findViewById(R.id.item_message_right_header_img);
            msgStatus = (ImageView) itemView.findViewById(R.id.item_message_msgStatus);
            Leftlay = itemView.findViewById(R.id.item_message_left_lay);
            rightlay = itemView.findViewById(R.id.item_message_right_lay);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {
        EMMessage item = list.get(position);
        if (item.getFrom().equals(EMClient.getInstance().getCurrentUser())) {
            holder.Leftlay.setVisibility(View.GONE);
            holder.rightlay.setVisibility(View.VISIBLE);

            if (item.getType()==TXT){
                EMTextMessageBody textMsg = (EMTextMessageBody) item.getBody();
                holder.right_content.setText(textMsg.getMessage());
            }
            if (item.getType() == IMAGE) {
                holder.right_content.setText("图片");
            }
        } else {

            holder.Leftlay.setVisibility(View.VISIBLE);
            holder.rightlay.setVisibility(View.GONE);
            if (item.getType() == TXT) {
                EMTextMessageBody textMsg = (EMTextMessageBody) item.getBody();
                holder.left_content.setText(textMsg.getMessage());

            }
            if (item.getType() == IMAGE) {
                holder.right_content.setText("图片");
            }

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

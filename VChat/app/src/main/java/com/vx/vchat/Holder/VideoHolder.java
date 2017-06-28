package com.vx.vchat.Holder;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.vx.vchat.R;
import com.vx.vchat.until.ImageUtil;

import static com.hyphenate.chat.EMMessage.Type.IMAGE;
import static com.hyphenate.chat.EMMessage.Type.TXT;

/**
 * Created by asus on 2017/5/23.
 */

public class ImageHolder extends RecyclerView.ViewHolder {
    private TextView time;
    private ImageView left_header, right_header, msgStatus, left_content, right_content;
    private View Leftlay, rightlay;

    public ImageHolder(View itemView) {
        super(itemView);
        time = (TextView) itemView.findViewById(R.id.time);
        left_content = (ImageView) itemView.findViewById(R.id.item_message_left_content_img);
        right_content = (ImageView) itemView.findViewById(R.id.item_message_right_content_img);
        left_header = (ImageView) itemView.findViewById(R.id.item_message_left_header_img);
        right_header = (ImageView) itemView.findViewById(R.id.item_message_right_header_img);
        Leftlay = itemView.findViewById(R.id.item_message_left_lay);
        rightlay = itemView.findViewById(R.id.item_message_right_lay);
    }

    public void setView(Context context, EMMessage item) {
        if (item.getFrom().equals(EMClient.getInstance().getCurrentUser())) {
            Leftlay.setVisibility(View.GONE);
            rightlay.setVisibility(View.VISIBLE);
//判断消息类型
            if (item.getType() == IMAGE) {
                //去除图片消息体
                EMImageMessageBody imgBody = (EMImageMessageBody) item.getBody();
                int width = ImageUtil.minWidth, height = ImageUtil.minHeight;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Size size = ImageUtil.getWidthAndHeight(imgBody.getWidth(), imgBody.getHeight());
                    width = size.getWidth();
                    height = size.getHeight();
                } else {
//                    TODO 小于API21时 使用其它办法
                }

                Glide.with(context).load(imgBody.getLocalUrl()).override(width, height).into(right_content);

            }
        } else {

            Leftlay.setVisibility(View.VISIBLE);
            rightlay.setVisibility(View.GONE);

            if (item.getType() == IMAGE) {
                EMImageMessageBody imageBody = (EMImageMessageBody) item.getBody();

                Glide.with(context).load(imageBody.getThumbnailUrl()).into(left_content);

            }

        }
    }
}

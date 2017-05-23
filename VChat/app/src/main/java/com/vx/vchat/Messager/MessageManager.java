package com.vx.vchat.Messager;

import com.vx.vchat.callback.MessageList;

/**
 * Created by asus on 2017/5/10.
 */

public class MessageManager {
    private MessageList messageList;

    public static MessageManager messageManager = new MessageManager();

    public static MessageManager getInstance() {
//        if (messageManager == null) {
//            messageManager = new MessageManager();
//        }
        return messageManager;
    }


    public MessageList getMessageList() {
        return messageList;
    }

    public void setMessageList(MessageList messageList) {
        this.messageList = messageList;
    }
}

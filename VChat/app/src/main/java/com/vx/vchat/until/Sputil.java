package com.vx.vchat.until;

import android.content.Context;
import android.content.SharedPreferences;


public class Sputil {
    private static final String SP_NAME = "USER";
    private static final String USER_NAME_KEY = "loginnsename";
    private static final String CHAT_DEFF = "chatdeff";
    private static SharedPreferences sp;

    public static String getChatDeff(Context context) {
        getSP(context);
        return sp.getString(CHAT_DEFF, "");
    }
    public static void setChatDeff(Context context, String json) {

        getSP(context);
        sp.edit().putString(CHAT_DEFF, json).apply();

    }





    public static void setloginUser(Context context, String username) {
        getSP(context);
        sp.edit().putString(USER_NAME_KEY, username).apply();

    }

    private static void getSP(Context context) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static String getLoginUser(Context context) {
        getSP(context);
        return sp.getString(USER_NAME_KEY, "");

    }
}

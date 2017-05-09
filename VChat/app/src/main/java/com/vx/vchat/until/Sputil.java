package com.vx.vchat.until;

import android.content.Context;
import android.content.SharedPreferences;


public class Sputil {
    private static final String SP_NAME = "USER";
    private static final String USER_NAME_KEY = "";
    private static SharedPreferences sp;

    public static void setloginUser(Context context, String username) {
        getSP(context);
        sp.edit().putString(USER_NAME_KEY, username).apply();

    }

    private static void getSP(Context context) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public  static String getLoginUser(Context context){
        getSP(context);
        return    sp.getString(USER_NAME_KEY,"");

    }
}

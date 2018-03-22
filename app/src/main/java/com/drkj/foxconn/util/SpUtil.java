package com.drkj.foxconn.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ganlong on 2018/1/24.
 * Modify by VeronicaRen on 2018/3/21.
 */
public class SpUtil {

    public static final String TOKEN = "token";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String TASK_ID = "taskId";

    public static final String REMEMBER = "remember";

    public static final String REAL_NAME = "realName";

    public static final String TASK_TYPE = "taskType";

    public static final String USER_ID = "userId";

    public static final String BASE_URL = "baseRul";

    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static void clearString(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, "");
        editor.apply();
    }
}

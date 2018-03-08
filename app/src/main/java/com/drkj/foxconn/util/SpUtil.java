package com.drkj.foxconn.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ganlong on 2018/1/24.
 */

public class SpUtil {

    public static void putString(Context context,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context,String key){
        SharedPreferences sp=context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String value = sp.getString(key,"");
        return value;
    }

    public static void putBlooean(Context context,String key,boolean value){
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBlooean(Context context,String key){
        SharedPreferences sp=context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key,false);
        return value;
    }
}

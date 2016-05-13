package com.example.administrator.guosounews.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    private static SharedPreferences sp;
    private static String SP_NAME = "config";

    public static void saveString(Context ct, String key, String value){
        if (sp == null) {
            sp = ct.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context ct, String key){
        if (sp == null) {
            sp = ct.getSharedPreferences(SP_NAME, 0);
        }
        return sp.getString(key, "");

    }
}

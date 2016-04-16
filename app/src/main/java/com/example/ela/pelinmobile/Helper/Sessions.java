package com.example.ela.pelinmobile.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by e on 16/04/16.
 */
public class Sessions {

    SharedPreferences preferences;
    Editor editor;
    Context context;
    public static String token;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "MyPref";
    private static final String IS_LOGIN = "isLoggedIn";
    private static final String KEY_NAME = "name";

    public Sessions(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(KEY_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }
}

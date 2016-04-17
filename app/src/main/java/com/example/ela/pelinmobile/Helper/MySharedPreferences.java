package com.example.ela.pelinmobile.Helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by e on 16/04/16.
 */
public class MySharedPreferences extends Application {
    private final String myPref = "myPrefs";
    private SharedPreferences sp;
    private Context _context;
    private SharedPreferences.Editor editor;

    public MySharedPreferences(Context context) {
        this._context = context;
        this.sp = _context.getSharedPreferences(myPref, MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public String getToken() {
        return sp.getString("token", null);
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }

    public void setUsername(String username) {
        editor.putString("username", username);
        editor.commit();
    }

    public String getUsername(String username) {
        return sp.getString("username", null);
    }

    public SharedPreferences getSharedPreferences() {
        return this.sp;
    }
}

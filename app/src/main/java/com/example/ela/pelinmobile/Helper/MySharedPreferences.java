package com.example.ela.pelinmobile.Helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.http.Body;

/**
 * Created by e on 16/04/16.
 */
public class MySharedPreferences extends Application {
    private final String myPref = "myPrefs";
    private SharedPreferences sp;
    private Context _context;
    public SharedPreferences.Editor editor;

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



    public void setStatus(boolean status) {
        editor.putBoolean("status", status);
        editor.commit();
    }

    public String getUserImage() {
        return sp.getString("url", null);
    }

    public void setUserImage(String url) {
        editor.putString("url", url);
        editor.commit();
    }

    public boolean getStatus() {
        return sp.getBoolean("status", false);
    }

    public String getUsername() {
        return sp.getString("username", "Username");
    }

    public SharedPreferences getSharedPreferences() {
        return this.sp;
    }

    public void deleteToken() {
        editor.clear();
        editor.commit();
    }

}

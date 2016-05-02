package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ela.pelinmobile.Helper.MySharedPreferences;

/**
 * Created by e on 2/05/16.
 */
public class Base extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySharedPreferences sharedPreferences = new MySharedPreferences(this);
        String token = sharedPreferences.getToken();
        if (token == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, HomeDosen.class);
            startActivity(intent);
        }
        finish();
    }
}

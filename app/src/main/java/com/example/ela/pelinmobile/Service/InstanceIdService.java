package com.example.ela.pelinmobile.Service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.ela.pelinmobile.Helper.MySharedPreferences;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.SendToken;
import com.example.ela.pelinmobile.Model.FirebaseTokenModel;
import com.example.ela.pelinmobile.Model.TokenModel;
import com.example.ela.pelinmobile.Model.User;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 29/05/16.
 */
public class InstanceIdService extends FirebaseInstanceIdService {
    String TAG = "respon";
//    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onTokenRefresh: " +  token);

        // TODO: Implement this method to send any registration to your app's servers.

//        saveToken(token);


//        if (sp.getString("firebaseToken", null) == null) {
//            saveToken(token);
//        } else {
//            saveToken(token);
//            sendTokenToServer(token);
//        }

        sendTokenToServer(token);

    }

//    private void saveToken(String token) {
//        SharedPreferences.Editor edit = sp.edit();
//        edit.putString("firebaseToken", token);
//        edit.commit();
//    }

    public void sendTokenToServer(final String token) {

        FirebaseTokenModel tokenModel = new FirebaseTokenModel();
        tokenModel.setReg_id(token);


        Log.d(TAG, "sendTokenToServer: " + token);

        SendToken service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(SendToken.class);
        Call<User> call = service.sendToken(tokenModel);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("tokenToServer", "onResponse: " + response.code() + " " + token);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}

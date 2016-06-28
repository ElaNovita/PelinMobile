package com.example.ela.pelinmobile.Helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.ela.pelinmobile.Interface.SendToken;
import com.example.ela.pelinmobile.Model.FirebaseTokenModel;
import com.example.ela.pelinmobile.Model.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by e on 16/04/16.
 */
public class RetrofitBuilder {
    public static final String BaseUrl = "http://192.168.12.1:8000/api/";
    private Context _context;

    public RetrofitBuilder(Context context) {
        this._context = context;
    }

     private OkHttpClient getClient() {
        final String token = new MySharedPreferences(this._context).getToken();

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization",
                        "Bearer " + token).build();
                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);

        return builder.build();
    }

    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
    }

    public void sendTokenToServer(final String token) {

        FirebaseTokenModel tokenModel = new FirebaseTokenModel();
        tokenModel.setReg_id(token);


        Log.d("respon", "sendTokenToServer: " + token);

        SendToken service = new RetrofitBuilder(_context).getRetrofit().create(SendToken.class);
        Call<User> call = service.sendToken(tokenModel);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                Log.d("tokenToServer", "onResponse: " + response.code() + " " + token);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}

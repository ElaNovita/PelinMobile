package com.example.ela.pelinmobile.Helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by e on 16/04/16.
 */
public class RetrofitBuilder {
    public static final String BaseUrl = "http://pelinapi-edsproject.rhcloud.com/api/";
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

}

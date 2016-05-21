package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.LoginModel;
import com.example.ela.pelinmobile.Model.TokenModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by e on 16/04/16.
 */
public interface Login {

    @POST("jwt")
    Call<TokenModel> login(@Body LoginModel model);
}

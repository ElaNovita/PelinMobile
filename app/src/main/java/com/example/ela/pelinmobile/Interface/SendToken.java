package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.FirebaseTokenModel;
import com.example.ela.pelinmobile.Model.TokenModel;
import com.example.ela.pelinmobile.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;

/**
 * Created by e on 28/06/16.
 */
public interface SendToken {

    @PATCH("users/me")
    Call<User> sendToken(@Body FirebaseTokenModel tokenModel);
}

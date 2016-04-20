package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by e on 18/04/16.
 */
public interface MyInterface {

    @GET("users/me")
    Call<User> getUser();
}

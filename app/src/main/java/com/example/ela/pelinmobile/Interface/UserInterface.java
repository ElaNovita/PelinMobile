package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by e on 16/04/16.
 */
public interface UserInterface {

    @GET("users")
    Call<List<User>> getUser();
}

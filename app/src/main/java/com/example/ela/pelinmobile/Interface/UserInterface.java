package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by e on 16/04/16.
 */
public interface UserInterface {

    @GET("users")
    Call<List<User>> getUser();

    @GET("users/{userId}")
    Call<User> getSingleUser(@Path("userId") int userId);

    @Multipart
    @PATCH("users/me")
    Call<User> editUser(@Part ("name") RequestBody name,
                        @Part ("phone") RequestBody phone,
                        @Part ("email") RequestBody email,
                        @Part ("password") RequestBody password,
                        @Part MultipartBody.Part photo);

//    @Multipart
//    @POST("groups/{groupId}/assignments/{assignId}/submit")
//    Call<SubmitModel> submitTugas(@Path("groupId") int groupId,
//                                  @Path("assignId") int assign,
//                                  @Part("text") RequestBody text,
//                                  @Part MultipartBody.Part file);
}

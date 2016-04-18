package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.GroupModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by e on 18/04/16.
 */
public interface GroupInterface {

    @GET("groups")
    Call<List<GroupModel>> getGroups();

    @POST("groups")
    Call<GroupModel> createGroup(@Body GroupModel groupModel);
}

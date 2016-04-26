package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.GroupModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by e on 22/04/16.
 */
public interface MyGroups {

    @GET("users/me/groups")
    Call<List<GroupModel>> getMyGroups();
}

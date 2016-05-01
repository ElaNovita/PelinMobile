package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.Model.GroupModel;
import com.example.ela.pelinmobile.Model.JoinModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by e on 18/04/16.
 */
public interface GroupInterface {

    @GET("groups")
    Call<List<GroupModel>> getGroups();

    @GET("groups/{groupId}")
    Call<GroupModel> getSingleGroup(@Path("groupId") int groupId);

    @POST("groups")
    Call<GroupModel> createGroup(@Body GroupModel groupModel);

    @GET("groups/{groupId}/join")
    Call<JoinModel> join(@Path("groupId") int groupId);

    @GET("groups/{groupId}/leave")
    Call<ApproveModel> leave(@Path("groupId") int groupId);
}

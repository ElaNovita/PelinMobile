package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.Model.MemberModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by e on 20/04/16.
 */
public interface MemberInterface {

    @GET("groups/{groupId}/members")
    Call<List<MemberModel>> getMembers(@Path("groupId") int groupId);

    @GET("groups/{groupId}/members/invite")
    Call<ApproveModel> invite(@Path("groupId") int groupId, @Query("nim") String nim);

}

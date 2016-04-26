package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.MemberModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by e on 20/04/16.
 */
public interface MemberInterface {

    @GET("groups/{groupId}/members")
    Call<List<MemberModel>> getMembers(@Path("groupId") int groupId);
}

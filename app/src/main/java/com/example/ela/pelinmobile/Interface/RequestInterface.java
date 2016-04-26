package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.RequestModel;
import com.example.ela.pelinmobile.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by e on 22/04/16.
 */
public interface RequestInterface {

    @GET("groups/{groupId}/pendings")
    Call<List<RequestModel>> getUsers(@Path("groupId") int groupId);

    @GET("groups/{groupId}/pendings/{idReq}/approve")
    Call<RequestModel> confirmUser(@Path("groupId") int groupId, @Path("reqId") int reqId);
}

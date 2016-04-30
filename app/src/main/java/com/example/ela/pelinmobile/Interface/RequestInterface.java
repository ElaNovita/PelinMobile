package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.Model.RequestModel;
import com.example.ela.pelinmobile.Model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by e on 22/04/16.
 */
public interface RequestInterface {

    @GET("groups/{groupId}/pendings")
    Call<List<RequestModel>> getUsers(@Path("groupId") int groupId);

    @GET("groups/{groupId}/pendings/{reqId}/approve")
    Call<ApproveModel> confirmUser(@Path("groupId") int groupId, @Path("reqId") int reqId);

    @GET("groups/{groupId}/pendings/approve_all")
    Call<ApproveModel> confirmAll(@Path("groupId") int groupId);

    @DELETE("messages/{userId}")
    Call<ResponseBody> deleteMsg(@Path("userId") String userId);

    @GET("groups/{groupId}/pendings/{reqId}/decline")
    Call<ResponseBody> decline(@Path("groupId") int groupId, @Path("reqId") int reqId);

}

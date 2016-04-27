package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.MateriModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by e on 20/04/16.
 */
public interface MateriInterface {

    @GET("groups/{groupId}/lessons")
    Call<List<MateriModel>> getMateri(@Path("groupId") int groupId);


    @Multipart
    @POST("groups/{groupId}/lessons")
    Call<MateriModel> createMateri(@Path("groupId") int groupId, MateriModel materiModel, @Part MultipartBody.Part file);

}
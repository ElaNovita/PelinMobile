package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.MateriModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by e on 20/04/16.
 */
public interface MateriInterface {

    @GET("groups/{groupId}/lessons")
    Call<List<MateriModel>> getMateri(@Path("groupId") int groupId);


    @Multipart
    @POST("groups/{groupId}/lessons")
    Call<MateriModel> createMateri(@Path("groupId") int groupId,
                                   @Part MultipartBody.Part files,
                                   @Part("title") RequestBody title,
                                   @Part("desc") RequestBody desc);

    @DELETE("groups/{groupId}/lessons/{materiId}")
    Call<ResponseBody> deleteMateri(@Path("groupId") int groupId, @Path("materiId") int materiId);

    @GET
    Call<ResponseBody> downloadMateri(@Url String materiUrl);

}

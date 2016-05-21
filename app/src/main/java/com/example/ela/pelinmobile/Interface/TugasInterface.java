package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.MyAssignment;
import com.example.ela.pelinmobile.Model.SubmitModel;
import com.example.ela.pelinmobile.Model.Submitted;
import com.example.ela.pelinmobile.Model.TugasModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by e on 20/04/16.
 */
public interface TugasInterface {

    @GET("groups/{groupId}/assignments")
    Call<List<TugasModel>> getTugas(@Path("groupId") int groupId);

    @GET("my_assignments")
    Call<List<MyAssignment>> getAllTugas();

    @DELETE("groups/{groupId}/assignments/{idTugas}")
    Call<ResponseBody> deleteTugas(@Path("groupId") int groupId, @Path("idTugas") int idTugas);

    @Multipart
    @POST("groups/{groupId}/assignments")
    Call<TugasModel> createTugas(@Path("groupId") int groupId,
                                 @Part MultipartBody.Part files,
                                 @Part("title") RequestBody title,
                                 @Part("description") RequestBody desc,
                                 @Part("due_date") RequestBody dueDate);

    @Multipart
    @POST("groups/{groupId}/assignments/{assignId}/submit")
    Call<SubmitModel> submitTugas(@Path("groupId") int groupId,
                                  @Path("assignId") int assign,
                                  @Part("text") RequestBody text,
                                  @Part MultipartBody.Part file);

    @GET("groups/{groupId}/assignments/{assignId}/submitted")
    Call<List<Submitted>> getSubmitted(@Path("groupId") int groupId, @Path("assignId") int assignId);

    @Multipart
    @PATCH("groups/{groupId}/assignments/{assignId}")
    Call<SubmitModel> editTugas(@Path("groupId") int groupId,
                               @Path("assignId") int assign,
                                @Part MultipartBody.Part files,
                                @Part("title") RequestBody title,
                                @Part("description") RequestBody desc,
                                @Part("due_date") RequestBody dueDate);

//    @GET("groups/{groupId}/assignments/{asignId}/")
}

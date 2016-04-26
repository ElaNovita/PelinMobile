package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.TugasModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by e on 20/04/16.
 */
public interface TugasInterface {

    @GET("groups/{groupId}/assignments")
    Call<List<TugasModel>> getTugas(@Path("groupId") int groupId);

    @GET("my_assignments")
    Call<List<TugasModel>> getAllTugas();
}

package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.DetailDiskusiModel;
import com.example.ela.pelinmobile.Model.DiskusiModel;
import com.example.ela.pelinmobile.Model.GroupModel;
import com.example.ela.pelinmobile.Model.NewPostModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by e on 18/04/16.
 */
public interface DiskusiInterface {

    @GET("groups/{groupId}/posts")
    Call<List<DiskusiModel>> getPost(@Path("groupId") int groupId);

    @POST("groups/{groupId}/posts")
    Call<DiskusiModel> createPost(@Path("groupId") int groupId, @Body NewPostModel diskusiModel);

    @DELETE("groups/{groupId}/posts/{postId}")
    Call<ResponseBody> deletePost(@Path("groupId") int groupId, @Path("postId") int postId);


}

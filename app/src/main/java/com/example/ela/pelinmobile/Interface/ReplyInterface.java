package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.DetailDiskusiModel;
import com.example.ela.pelinmobile.Model.ReplyModel;
import com.example.ela.pelinmobile.Model.VoteModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by e on 22/04/16.
 */
public interface ReplyInterface {

    @GET("groups/{groupId}/posts/{postId}/comments")
    Call<List<ReplyModel>> getReplys(@Path("groupId") int groupId, @Path("postId") int postId);

    @POST("groups/{groupId}/posts/{postId}/comments")
    Call<ReplyModel> postReply(@Path("groupId") int groupId, @Path("postId") int postId, @Body ReplyModel replyModel);

    @GET("groups/{groupId}/posts/{postId}")
    Call<DetailDiskusiModel> getPostDetail(@Path("groupId") int groupId, @Path("postId") int postId);

    @GET("groups/{groupId}/posts/{postId}/vote")
    Call<VoteModel> getVoted(@Path("groupId") int groupId, @Path("postId") int postId);
}

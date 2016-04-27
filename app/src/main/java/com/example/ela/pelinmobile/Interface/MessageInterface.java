package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.MessageDetailModel;
import com.example.ela.pelinmobile.Model.MessageModel;
import com.example.ela.pelinmobile.Model.ReplyMsgModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by e on 25/04/16.
 */
public interface MessageInterface {

    @GET("messages")
    Call<List<MessageModel>> getMessages();

    @GET("messages/{userId}")
    Call<List<MessageDetailModel>> getMessageDetail(@Path("userId") String userId);

    @POST("messages/{userId}/reply")
    Call<ReplyMsgModel> sendMsg(@Path("userId") String userId, @Body ReplyMsgModel replyMsgModel);

    @DELETE("messages/{userId}")
    Call<ResponseBody> deleteMsg(@Path("userId") String userId);
}

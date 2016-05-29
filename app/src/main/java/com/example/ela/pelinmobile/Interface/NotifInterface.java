package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.MarkReadModel;
import com.example.ela.pelinmobile.Model.NotifModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by e on 27/05/16.
 */
public interface NotifInterface {

    @GET("notifications")
    Call<List<NotifModel>> listNotif();

    @GET("notifications/mark_read")
    Call<MarkReadModel> markRead();

    @GET("notifications/clear")
    Call<ResponseBody> clearNotif();
}

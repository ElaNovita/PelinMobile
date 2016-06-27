package com.example.ela.pelinmobile.Interface;

import com.example.ela.pelinmobile.Model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by e on 27/06/16.
 */
public interface VideoInterface {

    @GET("videos")
    Call<List<Video>> getVideos();
}

package com.example.ela.pelinmobile.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e on 25/04/16.
 */
public class MessageModel {

    String url;

    @SerializedName("created_at")
    String createdAt;

    int id;

    @SerializedName("user_id")
    String userId;

    @SerializedName("target_user")
    TargetUser targetUser;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public TargetUser getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(TargetUser targetUser) {
        this.targetUser = targetUser;
    }
}

package com.example.ela.pelinmobile.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e on 24/04/16.
 */
public class DetailDiskusiModel {
    String text;

    @SerializedName("created_at")
    String createdAt;

    @SerializedName("votes_count")
    int votesCount;

    @SerializedName("is_voted")
    boolean isVoted;

    PhotoModel photo;

    User user;
    int id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    public PhotoModel getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoModel photo) {
        this.photo = photo;
    }
}

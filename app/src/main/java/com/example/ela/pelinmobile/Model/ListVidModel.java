package com.example.ela.pelinmobile.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by e on 17/06/16.
 */
public class ListVidModel {
    String title, description, users;

    List<String> category;

    @SerializedName("youtube_id")
    String youtubeId;

    User user;

    public ListVidModel() {
    }

    public ListVidModel(String title, String youtubeId, String users) {
        this.title = title;
        this.youtubeId = youtubeId;
        this.users = users;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}

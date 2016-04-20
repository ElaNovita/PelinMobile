package com.example.ela.pelinmobile.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e on 18/04/16.
 */
public class DiskusiModel {
    String text, created_at;
    Teacher teacher;
    User user;

    @SerializedName("group")
    int groupId;

    @SerializedName("votes_count")
    int votesCount;


    public String getCreated_at() {
        return created_at;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
    }
}

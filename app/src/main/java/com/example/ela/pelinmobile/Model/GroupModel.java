package com.example.ela.pelinmobile.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e on 18/04/16.
 */
public class GroupModel {
    String title;
    int members, id;
    Teacher teacher;

    @SerializedName("is_joined")
    boolean isJoined;

    //TODO change pending_approve to is_pending
    @SerializedName("is_pending")
    boolean isPending;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMember() {
        return members;
    }

    public void setMember(int member) {
        this.members = member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isJoined() {
        return isJoined;
    }

    public void setJoined(boolean joined) {
        isJoined = joined;
    }

    public boolean isPending() {

        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }
}

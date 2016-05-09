package com.example.ela.pelinmobile.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e on 18/04/16.
 */
public class GroupModel {
    String title, major, description;
    int members, id, semester;
    Teacher teacher;

    @SerializedName("is_owner")
    boolean isOwner;

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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }
}


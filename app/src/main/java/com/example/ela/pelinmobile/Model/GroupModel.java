package com.example.ela.pelinmobile.Model;

/**
 * Created by e on 18/04/16.
 */
public class GroupModel {
    String title;
    int members, id;
    Teacher teacher;

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
}

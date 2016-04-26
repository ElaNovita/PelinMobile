package com.example.ela.pelinmobile.Model;

/**
 * Created by e on 25/04/16.
 */
public class RequestModel {
    int id;
    Student student;
    User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

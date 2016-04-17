package com.example.ela.pelinmobile.Model;

/**
 * Created by e on 16/04/16.
 */
public class User {
    String status, name, email;
    User.Token token;
    User.Student student;
    User.Teacher teacher;
    User.Photo photo;
    int id;

    static class Token {
        String token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    static class Student {
        String major, nim;
    }

    static class Teacher {
        String nik, username;
    }

    static class Photo {
        String full, medium, small, thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

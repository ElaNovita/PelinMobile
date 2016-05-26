package com.example.ela.pelinmobile.Model;

/**
 * Created by e on 18/04/16.
 */
public class Student {
    String major, nim, name;
    PhotoModel photo;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhotoModel getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoModel photo) {
        this.photo = photo;
    }
}

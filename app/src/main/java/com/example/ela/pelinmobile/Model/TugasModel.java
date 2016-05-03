package com.example.ela.pelinmobile.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e on 20/04/16.
 */
public class TugasModel {

    String title, due_date, description;
    int id;

    @SerializedName("files")
    List<Object> files = new ArrayList<Object>();

    @SerializedName("created_at")
    String createdAt;

    @SerializedName("updated_at")
    String updatedAt;

    @SerializedName("is_passed")
    boolean isPassed;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Object> getFiles() {
        return files;
    }

    public void setFiles(List<Object> files) {
        this.files = files;
    }
}

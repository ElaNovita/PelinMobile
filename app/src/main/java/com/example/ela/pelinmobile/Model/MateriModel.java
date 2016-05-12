package com.example.ela.pelinmobile.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MateriModel {

    int id;

    String title, description;

    @SerializedName("created_at")
    String createdAt;

    @SerializedName("updated_at")
    String updatedAt;

    @SerializedName("files")
    List<FileModel> fileModels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<FileModel> getFileModels() {
        return fileModels;
    }

    public void setFileModels(List<FileModel> fileModels) {
        this.fileModels = fileModels;
    }
}

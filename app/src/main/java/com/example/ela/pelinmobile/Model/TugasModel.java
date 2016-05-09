package com.example.ela.pelinmobile.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e on 20/04/16.
 */
public class TugasModel {

    String title, description;
    int id;

    @SerializedName("due_date")
    String dueDate;

    @SerializedName("files")
    List<FilesModel> files;

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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public List<FilesModel> getFiles() {
        return files;
    }

    public void setFiles(List<FilesModel> files) {
        this.files = files;
    }
}

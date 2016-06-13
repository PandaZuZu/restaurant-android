package com.cpl.restaurantrezervation.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by txhung08 on 13/06/16.
 */
public class Achievements {
    private int id;
    private String title;
    private String description;
    @SerializedName("image")
    private Pictures pictures;


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

    public Pictures getPictures() {
        return pictures;
    }

    public void setPictures(Pictures pictures) {
        this.pictures = pictures;
    }

    public Achievements(int id, String title, String description, Pictures pictures) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pictures = pictures;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

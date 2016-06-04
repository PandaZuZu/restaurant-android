package com.cpl.restaurantrezervation.model;

/**
 * Created by txhung08 on 02/06/16.
 */
public class Restaurant {
    private int id;
    private String name;
    private String description;
    private String opened;
    private String website;
    private String phone;
    private double latitude;
    private double longitude;
    private Pictures pictures;

    public Restaurant(int id, String name, String description, String opened, String website, String phone, double latitude, double longitude, Pictures pictures) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.opened = opened;
        this.website = website;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pictures = pictures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Pictures getPictures() {
        return pictures;
    }

    public void setPictures(Pictures pictures) {
        this.pictures = pictures;
    }
}

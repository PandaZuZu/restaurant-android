package com.cpl.restaurantrezervation.model;

/**
 * Created by txhung08 on 03/06/16.
 */
public class Thumbnail {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public Thumbnail(String url) {
        this.url = url;
    }
}

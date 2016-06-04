package com.cpl.restaurantrezervation.model;

/**
 * Created by txhung08 on 03/06/16.
 */
public class Standard {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public Standard(String url) {
        this.url = url;
    }
}

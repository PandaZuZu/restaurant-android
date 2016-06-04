package com.cpl.restaurantrezervation.model;

/**
 * Created by txhung08 on 03/06/16.
 */
public class Pictures {

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    private String url;
    private Standard standard;
    private Thumbnail thumbnail;

    public Pictures(String url, Standard standard, Thumbnail thumbnail) {
        this.url = url;
        this.standard = standard;
        this.thumbnail = thumbnail;
    }
}

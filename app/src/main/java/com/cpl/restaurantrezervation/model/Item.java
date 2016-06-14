package com.cpl.restaurantrezervation.model;

/**
 * Created by txhung08 on 13/06/16.
 */
public class Item {
    private int id;
    private int price;
    private String title;

    public Pictures getPictures() {
        return pictures;
    }

    public void setPictures(Pictures pictures) {
        this.pictures = pictures;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Pictures pictures;

    public Item(int id, int price, String title, Pictures pictures) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.pictures = pictures;
    }
}

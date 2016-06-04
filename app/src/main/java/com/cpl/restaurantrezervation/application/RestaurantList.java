package com.cpl.restaurantrezervation.application;

import com.cpl.restaurantrezervation.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by txhung08 on 03/06/16.
 */
public class RestaurantList {
    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();

    public RestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}

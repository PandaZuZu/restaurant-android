package com.cpl.restaurantrezervation.model;

import java.util.List;

/**
 * Created by txhung08 on 01/06/16.
 */
public class User {
    private int id;
    private String email;
    private int coins;
    private List<Restaurant> restaurants;
    private List<Achievements> achievements;

    public User(int id, String email, int coins, List<Restaurant> restaurants, List<Achievements> achievements) {
        this.id = id;
        this.email = email;
        this.coins = coins;
        this.restaurants = restaurants;
        this.achievements = achievements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<Achievements> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievements> achievements) {
        this.achievements = achievements;
    }
}

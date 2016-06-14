package com.cpl.restaurantrezervation.application;

import com.cpl.restaurantrezervation.model.Achievements;
import com.cpl.restaurantrezervation.model.Item;
import com.cpl.restaurantrezervation.model.Restaurant;
import com.cpl.restaurantrezervation.model.User;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 *  Our API defined with get method to our online databse
 */
public interface ReservedAPI {
    @GET("user/login/{email}/{password}")
    Call<User> authenticate(@Path(value = "email", encoded = true) String email,
                            @Path(value = "password", encoded = true) String password);

    @GET("user/create/{email}/{password}")
    Call<User> register(@Path(value = "email", encoded = true) String email,
                            @Path(value = "password", encoded = true) String password);

    @GET("restaurant/show/{email}")
    Call<List<Restaurant>> getData(@Path(value = "email", encoded = true) String email);

    @GET("achievement/show/")
    Call<List<Achievements>> getAchievement();

    @GET("item/show/")
    Call<List<Item>> getShopItems();

    @GET("item/buy/{email}/{item}")
    Call<User> buyItem(@Path(value = "email" , encoded = true) int user_id,
                         @Path(value = "item" , encoded = true) int item_id);

    @GET("user/visited/{email}/{location}")
    Call<User> visitedLocation(@Path(value = "email", encoded = true) int user_id,
                                 @Path(value = "location", encoded = true) int location_id);
}

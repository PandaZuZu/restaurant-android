package com.cpl.restaurantrezervation.application;

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

    @GET("restaurant/show")
    Call<List<Restaurant>> getData();
}

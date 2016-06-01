package com.cpl.restaurantrezervation.application;

import com.cpl.restaurantrezervation.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by txhung08 on 01/06/16.
 */
public interface ReservedAPI {
    @GET("login/{email}/{password}")
    Call<User> authenticate(@Path("email") String email,
                            @Path("password") String password);

    @GET("create/{email}/{password}")
    Call<User> register(@Path("email") String email,
                            @Path("password") String password);
}

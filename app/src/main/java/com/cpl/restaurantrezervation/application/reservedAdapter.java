package com.cpl.restaurantrezervation.application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by txhung08 on 01/06/16.
 */
public class ReservedAdapter {

    public static final String BASE_URL = "https://restaurant-reserved.herokuapp.com";

    private ReservedAPI reservedAPI;

    public ReservedAdapter(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        reservedAPI = retrofit.create(ReservedAPI.class);
    }

    public ReservedAPI getReservedAPI(){
        return reservedAPI;
    }
}

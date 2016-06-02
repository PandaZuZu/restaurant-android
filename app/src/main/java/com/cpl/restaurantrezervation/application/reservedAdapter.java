package com.cpl.restaurantrezervation.application;

import android.util.Log;

import java.util.logging.Level;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by txhung08 on 01/06/16.
 */
public class ReservedAdapter {

    public static final String BASE_URL = "https://restaurant-reserved.herokuapp.com/";

    private ReservedAPI reservedAPI;

    public ReservedAdapter(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        reservedAPI = retrofit.create(ReservedAPI.class);
    }

    public ReservedAPI getReservedAPI(){
        return reservedAPI;
    }
}

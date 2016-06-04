package com.cpl.restaurantrezervation.application;

import android.app.Application;

/**
 *  Application for retrofit
 */
public class ReservedApplication extends Application {

    private ReservedAdapter reservedAdapter;

    @Override
    public void onCreate(){
        super.onCreate();

        reservedAdapter = new ReservedAdapter();

    }

    public ReservedAPI getReservedAPI(){
        return reservedAdapter.getReservedAPI();
    }
}

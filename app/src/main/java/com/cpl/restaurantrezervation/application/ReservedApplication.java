package com.cpl.restaurantrezervation.application;

import android.app.Application;

/**
 * Created by txhung08 on 01/06/16.
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

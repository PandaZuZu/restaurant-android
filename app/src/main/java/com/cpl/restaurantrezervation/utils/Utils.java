package com.cpl.restaurantrezervation.utils;

import android.graphics.Bitmap;

import com.cpl.restaurantrezervation.R;

import java.util.Random;

/**
 * Created by txhung08 on 01/06/16.
 */
public class Utils {

    private static final int backgroundNumbers = 2;
    private static final int[] drawable = {
            R.drawable.rsz_restaurant_background,
            R.drawable.restaurant_background2
    };

    public static String parseURL(String URL){
        String parsedURL = URL;
        String[] toChange = {"(?:@)", "\\."};
        String[] changeWith = {"%40", "%2E"};

        for(int i = 0; i < toChange.length; i++){
            parsedURL = parsedURL.replaceAll(toChange[i], changeWith[i]);
        }

        return parsedURL;
    }

    public static int setRandomBitmap(){
        Random random = new Random();
        switch (random.nextInt(drawable.length)){
            case 0:
                return R.drawable.rsz_restaurant_background;
            case 1:
                return R.drawable.restaurant_background2;
        }

        return 0;
    }
}

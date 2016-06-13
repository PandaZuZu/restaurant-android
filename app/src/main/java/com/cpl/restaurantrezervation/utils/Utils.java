package com.cpl.restaurantrezervation.utils;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.model.Coordinate;

import java.util.Random;

/**
 * Created by txhung08 on 01/06/16.
 */
public class Utils {

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

        if(drawable.length > 0){
            return drawable[random.nextInt(drawable.length)];
        }

        return 0;
    }

    public static double distanceBetweenPoints(Coordinate a, Coordinate b){
        double sumDouble = Math.pow(b.getLatitude() - a.getLatitude(), 2) + Math.pow(b.getLongitude() - a.getLongitude(), 2);
        return Math.sqrt(sumDouble);
    }
}

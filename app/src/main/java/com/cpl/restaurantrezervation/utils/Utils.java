package com.cpl.restaurantrezervation.utils;

/**
 * Created by txhung08 on 01/06/16.
 */
public class Utils {

    public static String parseURL(String URL){
        String parsedURL = URL;
        String[] toChange = {"(?:@)", "\\."};
        String[] changeWith = {"%40", "%2E"};

        for(int i = 0; i < toChange.length; i++){
            parsedURL = parsedURL.replaceAll(toChange[i], changeWith[i]);
        }

        return parsedURL;
    }
}

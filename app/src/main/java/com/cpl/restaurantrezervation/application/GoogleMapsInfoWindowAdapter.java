package com.cpl.restaurantrezervation.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.activity.RestaurantMapActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


/**
 *  Adapter for the InfoWindowAdapter.
 *  here we personalise information to be shown
 */
public class GoogleMapsInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;

    public GoogleMapsInfoWindowAdapter(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myContentsView = layoutInflater.inflate(R.layout.custom_info_contents, new LinearLayout(context), false);

    }


    @Override
    public View getInfoWindow(final Marker marker) {

        Bitmap bitmap = null;
        if(marker != null) {
            if (RestaurantMapActivity.restaurantImages.get(marker.getId()) != null) {
                bitmap = RestaurantMapActivity.restaurantImages.get(marker.getId());
            }

            if (bitmap != null) {

                final ImageView imageView = (ImageView) myContentsView.findViewById(R.id.restaurantPicture);
                imageView.setImageBitmap(bitmap);

            }

            final TextView nameText = (TextView) myContentsView.findViewById(R.id.name);
            nameText.setText(marker.getTitle());

            final TextView description = (TextView) myContentsView.findViewById(R.id.description);
            description.setText(marker.getSnippet());

        }
        return myContentsView;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}

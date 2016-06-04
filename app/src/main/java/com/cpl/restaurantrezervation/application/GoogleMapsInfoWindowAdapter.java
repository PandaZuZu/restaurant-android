package com.cpl.restaurantrezervation.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.activity.RestaurantMapActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by txhung08 on 03/06/16.
 */
public class GoogleMapsInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    private ImageLoader imageLoader;

    public GoogleMapsInfoWindowAdapter(Context context, ImageLoader imageLoader) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myContentsView = layoutInflater.inflate(R.layout.custom_info_contents, null);

        this.imageLoader = imageLoader;
    }


    @Override
    public View getInfoWindow(final Marker marker) {


        Bitmap bitmap = null;

        if(marker != null && RestaurantMapActivity.restaurantImages.get(marker.getId()) != null) {
            bitmap = RestaurantMapActivity.restaurantImages.get(marker.getId());
        }

        if(bitmap != null) {

            final ImageView imageView = (ImageView) myContentsView.findViewById(R.id.restaurantPicture);
            imageView.setImageBitmap(bitmap);

        }

        final TextView nameText = (TextView) myContentsView.findViewById(R.id.name);
        nameText.setText(marker.getTitle());

        final TextView description = (TextView) myContentsView.findViewById(R.id.description);
        description.setText(marker.getSnippet());

        return myContentsView;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}

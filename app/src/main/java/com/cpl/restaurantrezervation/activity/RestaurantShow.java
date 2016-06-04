package com.cpl.restaurantrezervation.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.model.Restaurant;
import com.cpl.restaurantrezervation.model.RestaurantList;
import com.cpl.restaurantrezervation.utils.Utils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/*
 * Template to show detailed information about restaurants
 */
public class RestaurantShow extends AppCompatActivity implements View.OnClickListener{

    private TextView restaurantNameField;
    private TextView restaurantDescriptionField;
    private TextView restaurantOpenedField;
    private TextView restaurantURL;
    private TextView restaurantPhoneField;
    private ImageView restaurantImageView;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_show);

        String restName = getIntent().getStringExtra(RestaurantMapActivity.RESTAURANT_REFERENCE_TAG);

        getReferences();

        if(restName != null) {
            Restaurant rs = findRestaurant(restName);
            if(rs != null){
                setInformation(rs);
            }
        }

        initImageLoader();

    }

    private Restaurant findRestaurant(String name){
        for(Restaurant rs : RestaurantList.restaurantList){
            if(rs.getName().compareTo(name) == 0){
                return rs;
            }
        }

        return null;
    }

    //we configure the imageLoader then initiate it
    private void initImageLoader(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(false) // default
                .cacheOnDisk(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getBaseContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options)
                .build();

        imageLoader.init(config);

    }

    private void setInformation(Restaurant rs){
        restaurantNameField.setText(rs.getName());
        restaurantDescriptionField.setText(rs.getDescription());
        restaurantOpenedField.setText(rs.getOpened());

        //add listeners too
        restaurantURL.setText(rs.getWebsite());
        restaurantPhoneField.setText(rs.getPhone());
        restaurantURL.setOnClickListener(this);
        restaurantPhoneField.setOnClickListener(this);

        addPicture(rs.getPictures().getUrl());
    }

    private void addPicture(String url){
        imageLoader.displayImage(url, restaurantImageView, new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri,
                                          View view, Bitmap loadedImage) {
                restaurantImageView.setImageBitmap(loadedImage);
            }
        });
    }

    private void getReferences(){
        restaurantNameField = (TextView) findViewById(R.id.name);
        restaurantDescriptionField= (TextView) findViewById(R.id.description);
        restaurantOpenedField = (TextView) findViewById(R.id.openedText);
        restaurantURL = (TextView) findViewById(R.id.websiteURL);
        restaurantPhoneField = (TextView) findViewById(R.id.phoneNumber);
        restaurantImageView = (ImageView) findViewById(R.id.restaurantPicture);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.restaurantView);
        relativeLayout.setBackground(getResources().getDrawable(Utils.setRandomBitmap(), null));
    }


    @Override
    public void onClick(View v) {

    }
}

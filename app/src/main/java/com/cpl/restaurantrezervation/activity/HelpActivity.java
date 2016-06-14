package com.cpl.restaurantrezervation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.utils.Utils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView locationMap;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private final String GOOGLE_STATIC_URL = "http://maps.googleapis.com/maps/api/staticmap?center=44.460969,26.128726&zoom=17&scale=false&size=900x1600&maptype=roadmap&format=png&visual_refresh=true&markers=size:mid%7Ccolor:0xff0000%7Clabel:1%7C44.460969,26.128726";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        locationMap = (ImageView) findViewById(R.id.googleMap);
        setReferences();
        initImageLoader();
        loadImage(GOOGLE_STATIC_URL);

    }

    private void setReferences(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.helpView);
        if(relativeLayout != null){
            relativeLayout.setBackground(getResources().getDrawable(Utils.setRandomBitmap(), null));
        }

        ImageButton compass = (ImageButton) findViewById(R.id.compass);
        ImageButton achievement = (ImageButton) findViewById(R.id.achievement);
        ImageButton shop = (ImageButton) findViewById(R.id.shop);
        ImageButton help = (ImageButton) findViewById(R.id.help);

        if(compass != null) {
            compass.setOnClickListener(this);
        }
        if(achievement != null) {
            achievement.setOnClickListener(this);
        }
        if(shop != null) {
            shop.setOnClickListener(this);
        }
        if(help != null) {
            //help.setOnClickListener(this);
        }
    }

    private void loadImage(String url){
        imageLoader.displayImage(url, locationMap, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri,
                                          View view, Bitmap loadedImage) {

                locationMap.setImageBitmap(loadedImage);
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.compass:
                Intent mapActivity = new Intent(this, RestaurantMapActivity.class);
                startActivity(mapActivity);
                break;
            case R.id.achievement:
                Intent achievementActivity = new Intent(this, AchievementActivity.class);
                startActivity(achievementActivity);
                break;
            case R.id.shop:
                Intent shopActivity = new Intent(this, ShopActivity.class);
                startActivity(shopActivity);
                break;
            case R.id.help:
                Intent helpActivity = new Intent(this, HelpActivity.class);
                startActivity(helpActivity);
                break;
            default:
                break;
        }
    }

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

}

package com.cpl.restaurantrezervation.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.application.ReservedApplication;
import com.cpl.restaurantrezervation.model.AchievementList;
import com.cpl.restaurantrezervation.model.Achievements;
import com.cpl.restaurantrezervation.model.User;
import com.cpl.restaurantrezervation.utils.Utils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * main activity of the application
 */

public class MainActivity extends Activity implements View.OnClickListener{

    public static User currentUser;

    private TextView coinsText;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public static HashMap<Integer, Bitmap> achievementImages = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setReferences();
        initImageLoader();
        getDataFromWeb();

    }

    private void setReferences(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.mainView);
        relativeLayout.setBackground(getResources().getDrawable(Utils.setRandomBitmap(), null));

        ImageButton compass = (ImageButton) findViewById(R.id.compass);
        ImageButton achievement = (ImageButton) findViewById(R.id.achievement);
        ImageButton shop = (ImageButton) findViewById(R.id.shop);
        ImageButton help = (ImageButton) findViewById(R.id.help);

        compass.setOnClickListener(this);
        achievement.setOnClickListener(this);
        shop.setOnClickListener(this);
        help.setOnClickListener(this);

        coinsText = (TextView) findViewById(R.id.coinsText);
        coinsText.setText(currentUser.getCoins() + " ");

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
                break;
            case R.id.help:
                break;
            default:
                break;
        }
    }

    private void getDataFromWeb(){
        Call<List<Achievements>> result = ((ReservedApplication)getApplication())
                .getReservedAPI().getAchievement();

        result.enqueue(new Callback<List<Achievements>>() {
            @Override
            public void onResponse(Call<List<Achievements>> call, Response<List<Achievements>> response) {

                AchievementList.achievementsList = response.body();

                for (Achievements achievement : AchievementList.achievementsList) {
                    getImageFromUrl(achievement.getPictures().getUrl(), achievement.getId());
                }
            }

            @Override
            public void onFailure(Call<List<Achievements>> call, Throwable t) {
                Log.d("body", "error");
            }
        });
    }

    //for each achievement we add bitmap loaded to our hash so we can add it on view later
    private void getImageFromUrl(String url, final int achievementID){
        imageLoader.loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri,
                                          View view, Bitmap loadedImage) {

                achievementImages.put(achievementID, loadedImage);
            }
        });
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

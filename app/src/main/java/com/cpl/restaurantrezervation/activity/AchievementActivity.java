package com.cpl.restaurantrezervation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.application.ReservedApplication;
import com.cpl.restaurantrezervation.model.AchievementList;
import com.cpl.restaurantrezervation.model.Achievements;
import com.cpl.restaurantrezervation.utils.Utils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchievementActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout doneLinearLayout;
    LinearLayout notDoneLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        setReferences();

        setUp();

    }

    private void setUp(){
        List <Achievements> achievements = AchievementList.achievementsList;
        List <Achievements> userAchievements = MainActivity.currentUser.getAchievements();

        for(int i = 0; i < achievements.size(); i++) {
            boolean goAdd = true;
            for(Achievements achievement:userAchievements) {
                if (achievements.get(i).getId() == achievement.getId()) {
                    goAdd = false;
                    break;
                }
            }
            if (!goAdd) {
                addAchievements(MainActivity.achievementImages.get(achievements.get(i).getId()), achievements.get(i).getDescription());
            } else {
                addAchievementsNotDown(MainActivity.achievementImages.get(achievements.get(i).getId()), achievements.get(i).getDescription());
            }
        }

    }

    private void setReferences(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.achievementView);
        if(relativeLayout != null) {
            relativeLayout.setBackground(getResources().getDrawable(Utils.setRandomBitmap(), null));
        }
        doneLinearLayout = (LinearLayout) findViewById(R.id.doneAchievements);
        notDoneLinearLayout = (LinearLayout) findViewById(R.id.notDoneAchievements);

        ImageButton compass = (ImageButton) findViewById(R.id.compass);
        ImageButton achievement = (ImageButton) findViewById(R.id.achievement);
        ImageButton shop = (ImageButton) findViewById(R.id.shop);
        ImageButton help = (ImageButton) findViewById(R.id.help);

        if(compass != null) {
            compass.setOnClickListener(this);
        }
        if(achievement != null) {
            //achievement.setOnClickListener(this);
        }
        if(shop != null) {
            shop.setOnClickListener(this);
        }
        if(help != null) {
            help.setOnClickListener(this);
        }


    }

    private void addAchievements(Bitmap achievementBitmap, String achievementDescription){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackground(getResources().getDrawable(R.drawable.application_gradient_background, null));
        linearLayout.setPadding((int) getResources().getDimension(R.dimen.image_margin),
                (int) getResources().getDimension(R.dimen.image_margin),
                (int) getResources().getDimension(R.dimen.image_margin),
                (int) getResources().getDimension(R.dimen.image_margin));
        linearLayout.setWeightSum(2);

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(achievementBitmap);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams((int)getResources().getDimension(R.dimen.icons_size),
                              (int)getResources().getDimension(R.dimen.icons_size), .75f);

        imageView.setLayoutParams(params);

        TextView textView = new TextView(this);
        textView.setText(achievementDescription);
        textView.setTextSize(15);

        params = new LinearLayout
                .LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.25f);

        textView.setLayoutParams(params);


        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        this.doneLinearLayout.addView(linearLayout);
    }

    private void addAchievementsNotDown(Bitmap achievementBitmap, String achievementDescription){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackground(getResources().getDrawable(R.drawable.application_gradient_background, null));
        linearLayout.setPadding((int) getResources().getDimension(R.dimen.image_margin),
                (int) getResources().getDimension(R.dimen.image_margin),
                (int) getResources().getDimension(R.dimen.image_margin),
                (int) getResources().getDimension(R.dimen.image_margin));
        linearLayout.setWeightSum(2);

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(achievementBitmap);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams((int)getResources().getDimension(R.dimen.icons_size),
                (int)getResources().getDimension(R.dimen.icons_size), .75f);

        imageView.setLayoutParams(params);

        TextView textView = new TextView(this);
        textView.setText(achievementDescription);
        textView.setTextSize(15);

        params = new LinearLayout
                .LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.25f);

        textView.setLayoutParams(params);


        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        this.notDoneLinearLayout.addView(linearLayout);
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
}


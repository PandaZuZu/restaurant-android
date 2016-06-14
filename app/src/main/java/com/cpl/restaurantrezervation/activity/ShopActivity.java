package com.cpl.restaurantrezervation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.application.ReservedApplication;
import com.cpl.restaurantrezervation.model.Item;
import com.cpl.restaurantrezervation.model.Shop;
import com.cpl.restaurantrezervation.model.User;
import com.cpl.restaurantrezervation.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout shopLinearLayout;
    public static final String NOT_ENOUGH_MONEY = "Not enough coins!";
    public static final String ITEM_BOUGHT = "You've bought an item.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        setReferences();
        setUpItems();
    }

    private void setUpItems(){
        for(Item item: Shop.shopItems){
            addShopItems(MainActivity.shopItemImages.get(item.getId()), item.getTitle(), item.getPrice(), item.getId());
        }
    }

    private void setReferences(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.shopView);
        if(relativeLayout != null) {
            relativeLayout.setBackground(getResources().getDrawable(Utils.setRandomBitmap(), null));
        }
        shopLinearLayout = (LinearLayout) findViewById(R.id.shopLayout);

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
            //shop.setOnClickListener(this);
        }
        if(help != null) {
            help.setOnClickListener(this);
        }
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

    private void addShopItems(Bitmap shopItemBitmap, String shopDescription, final int price, final int itemId){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackground(getResources().getDrawable(R.drawable.application_gradient_background, null));
        linearLayout.setPadding((int) getResources().getDimension(R.dimen.image_margin),
                (int) getResources().getDimension(R.dimen.image_margin),
                (int) getResources().getDimension(R.dimen.image_margin),
                (int) getResources().getDimension(R.dimen.image_margin));
        linearLayout.setWeightSum(4);

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(shopItemBitmap);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams((int)getResources().getDimension(R.dimen.icons_size),
                (int)getResources().getDimension(R.dimen.icons_size), 1f);

        imageView.setLayoutParams(params);

        TextView textView = new TextView(this);
        textView.setText(shopDescription);
        textView.setTextSize(15);

        params = new LinearLayout
                .LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);

        textView.setLayoutParams(params);

        Button buyButton = new Button(this);
        buyButton.setText(price + " ");
        buyButton.setTextSize(30);
        buyButton.setBackground(getResources().getDrawable(R.drawable.dollar_bill, null));

        params = new LinearLayout
                .LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        buyButton.setLayoutParams(params);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        linearLayout.addView(buyButton);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.currentUser.getCoins() < price) {
                    Toast.makeText(getApplicationContext(), NOT_ENOUGH_MONEY, Toast.LENGTH_SHORT).show();
                } else {
                    Call<User> result = ((ReservedApplication) getApplication())
                            .getReservedAPI().buyItem(MainActivity.currentUser.getId(), itemId);

                    result.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.body() != null) {
                                int coins = MainActivity.currentUser.getCoins();
                                MainActivity.currentUser.setCoins(coins - price);
                                MainActivity.coinsText.setText((coins - price) + " ");
                                Toast.makeText(getApplicationContext(), ITEM_BOUGHT, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), LoginActivity.DATABASE_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        this.shopLinearLayout.addView(linearLayout);
    }

}

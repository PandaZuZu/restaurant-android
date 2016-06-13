package com.cpl.restaurantrezervation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.application.ReservedApplication;
import com.cpl.restaurantrezervation.model.User;
import com.cpl.restaurantrezervation.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 *   login to Heroku server. check the response
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;

    public static final String DATABASE_ERROR = "Could not connect to database!";
    public static final String WRONG_INPUT = "Email or password must not be empty!";
    public static final String LOGIN_SUCCESS = "Logeed in as ";
    public static final String USERNAME_WRONG = "Email or password does not match!";
    public static final String NO_USER_FOUND = "user not found";

    private ProgressBar loginSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginSpinner = (ProgressBar) findViewById(R.id.loginSpinner);
        loginSpinner.setVisibility(View.GONE);

        setupReferences();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                String usernameText = usernameEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();

                checkLogin(usernameText, passwordText);

                loginSpinner.setVisibility(View.VISIBLE);

                break;
            case R.id.registerLink:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            default:
                break;
        }
    }

    private void checkLogin(String username, String password){
        if(username.length() > 0 && password.length() > 0){

            Call<User> result = ((ReservedApplication) getApplication())
                    .getReservedAPI().authenticate(Utils.parseURL(username), Utils.parseURL(password));


            result.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    String result = response.body().getEmail();
                    loginSpinner.setVisibility(View.GONE);

                    if(!result.contains(NO_USER_FOUND)) {
                        MainActivity.currentUser = response.body();
                        Toast.makeText(getApplicationContext(), LOGIN_SUCCESS + result, Toast.LENGTH_SHORT).show();
                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);
                        finish();
                    }
                    else{
                        resetInput();
                        Toast.makeText(getApplicationContext(), USERNAME_WRONG, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    loginSpinner.setVisibility(View.GONE);
                    Toast.makeText(getApplication(), DATABASE_ERROR, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            resetInput();
            loginSpinner.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), WRONG_INPUT, Toast.LENGTH_SHORT).show();
        }
    }

    private void resetInput(){
        usernameEditText.setText(null);
        passwordEditText.setText(null);
    }

    private void setupReferences(){
        Button logInButton;
        TextView registerTextView;

        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        logInButton = (Button)findViewById(R.id.loginButton);
        registerTextView = (TextView)findViewById(R.id.registerLink);

        logInButton.setOnClickListener(this);
        registerTextView.setOnClickListener(this);

        ScrollView scrollView = (ScrollView) findViewById(R.id.loginScrollView);
        scrollView.setBackground(getResources().getDrawable(Utils.setRandomBitmap(), null));

    }
}

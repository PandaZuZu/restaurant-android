package com.cpl.restaurantrezervation.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    login to heroku server. check the response
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button logInButton;
    private TextView registerTextView;

    public static final String DATABASE_ERROR = "Could not connect to database!";
    public static final String WRONG_INPUT = "Email or password must not be empty!";

    private ProgressBar loginSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
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
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        logInButton = (Button)findViewById(R.id.loginButton);
        registerTextView = (TextView)findViewById(R.id.registerLink);

        logInButton.setOnClickListener(this);
        registerTextView.setOnClickListener(this);

    }
}

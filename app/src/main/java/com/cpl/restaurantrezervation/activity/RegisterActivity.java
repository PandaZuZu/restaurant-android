package com.cpl.restaurantrezervation.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.application.ReservedApplication;
import com.cpl.restaurantrezervation.model.User;
import com.cpl.restaurantrezervation.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 *  try request to create user on heroku
 */

public class RegisterActivity extends Activity {

    private EditText passwordEditText;
    private EditText confirmationEditText;
    private EditText emailEditText;

    public static final String PASSWORD_ERROR_TOAST = "Password confirmation does not match!";
    public static final String PASSWORD_LENGTH_ERROR = "Password length must be between 8 and 20!";
    public static final String EMAIL_LENGTH_ERROR = "Email length must be between 5 and 100!";
    public static final String REGISTER_SUCCES_TOAST = "Registration Successful!";
    public static final String USER_TAKEN_ERROR = "user not found";
    public static final String REGISTRATION_FAIL = "Email already exists!";

    private ProgressBar registerSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupReferences();
    }

    private void setupReferences(){
        Button registerButton;

        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        confirmationEditText = (EditText)findViewById(R.id.passwordConfirmationEditText);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        registerButton = (Button)findViewById(R.id.registerButton);

        registerSpinner = (ProgressBar) findViewById(R.id.registerSpinner);
        registerSpinner.setVisibility(View.GONE);

        ScrollView scrollView = (ScrollView) findViewById(R.id.registerView);
        scrollView.setBackground(getResources().getDrawable(Utils.setRandomBitmap(), null));

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordText = passwordEditText.getText().toString();
                String confirmationText = confirmationEditText.getText().toString();
                String emailText = emailEditText.getText().toString();

                try {
                    tryRegister(passwordText, confirmationText, emailText);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void tryRegister(String passwordText,
                             String confirmationText,
                             String emailText) throws InterruptedException {

        if(passwordText.compareTo(confirmationText) != 0){
            resetInput();
            Toast.makeText(getApplicationContext(), PASSWORD_ERROR_TOAST, Toast.LENGTH_SHORT).show();
        }
        else if(passwordText.length() < 8 && passwordText.length() > 20) {
            resetInput();
            Toast.makeText(getApplicationContext(), PASSWORD_LENGTH_ERROR, Toast.LENGTH_SHORT).show();
        }
        else if(emailText.length() < 5 || emailText.length() > 100){
            resetInput();
            Toast.makeText(getApplicationContext(), EMAIL_LENGTH_ERROR, Toast.LENGTH_SHORT).show();
        }
        else{

            registerSpinner.setVisibility(View.VISIBLE);

            Call<User> result = ((ReservedApplication) getApplication()).getReservedAPI()
                    .register(Utils.parseURL(emailText), Utils.parseURL(passwordText));
            result.enqueue(new Callback<User>() {

                /*
                    if get response, check if it user saved
                 */
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    String result = response.body().getEmail();

                    if(!result.contains(USER_TAKEN_ERROR)) {
                        new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected void onPostExecute(Void result) {
                                registerSpinner.setVisibility(View.GONE);
                                finish();
                            }

                            @Override
                            protected void onPreExecute() {
                                Toast.makeText(getApplicationContext(), REGISTER_SUCCES_TOAST, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            protected Void doInBackground(Void... params) {
                                // Try to sleep for roughly 2 seconds
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }.execute();
                    }
                    else{
                        registerSpinner.setVisibility(View.GONE);
                        resetInput();
                        Toast.makeText(getApplicationContext(), REGISTRATION_FAIL, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    resetInput();
                    registerSpinner.setVisibility(View.GONE);
                    Log.e("error", t.getMessage());
                    Toast.makeText(getApplicationContext(), LoginActivity.DATABASE_ERROR, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void resetInput(){
        passwordEditText.setText(null);
        confirmationEditText.setText(null);
        emailEditText.setText(null);
    }
}

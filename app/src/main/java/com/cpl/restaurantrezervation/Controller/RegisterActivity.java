package com.cpl.restaurantrezervation.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpl.restaurantrezervation.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmationEditText;
    private EditText emailEditText;
    private Button registerButton;

    private final String PASSWORD_ERROR_TOAST = "Password confirmation does not match!";
    private final String REGISTER_ERROR_TOAST = "Could not register. Please try again!";
    private final String REGISTER_SUCCES_TOAST = "Registration Successful!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupReferences();
    }

    private void setupReferences(){
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        confirmationEditText = (EditText)findViewById(R.id.passwordConfirmationEditText);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        registerButton = (Button)findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = usernameEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();
                String confirmationText = confirmationEditText.getText().toString();
                String emailText = emailEditText.getText().toString();

                try {
                    tryRegister(usernameText, passwordText, confirmationText, emailText);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void tryRegister(String usernameText,
                             String passwordText,
                             String confirmationText,
                             String emailText) throws InterruptedException {

        if(passwordText.compareTo(confirmationText) != 0){
            resetInput();
            Toast.makeText(getApplicationContext(), PASSWORD_ERROR_TOAST, Toast.LENGTH_SHORT).show();
        }else{
            new AsyncTask<Void, Void, Void>(){

                @Override
                protected void onPostExecute(Void result) {
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
    }

    private void resetInput(){
        usernameEditText.setText(null);
        passwordEditText.setText(null);
        confirmationEditText.setText(null);
        emailEditText.setText(null);
    }
}

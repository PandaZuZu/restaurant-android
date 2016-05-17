package com.cpl.restaurantrezervation.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cpl.restaurantrezervation.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button logInButton;
    private TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupReferences();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                String usernameText = usernameEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();

                if(checkLogin(usernameText, passwordText)){
                    Intent reservationIntent = new Intent(this, RegisterActivity.class);
                    startActivity(reservationIntent);
                }
                break;
            case R.id.registerLink:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            default:
                break;
        }
    }

    private boolean checkLogin(String username, String password){
        if(username.contains("da")){
            return false;
        }
        return true;
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

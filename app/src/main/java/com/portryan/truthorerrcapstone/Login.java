package com.portryan.truthorerrcapstone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText username = (EditText)findViewById(R.id.text_username);
        EditText password = (EditText)findViewById(R.id.text_password);
        Button loginButton = (Button)findViewById(R.id.button_login);
        Button signupButton = (Button)findViewById(R.id.button_signup);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Login - Sign Up");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO - Check if user exists

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
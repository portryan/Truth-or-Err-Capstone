package com.portryan.truthorerrcapstone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    DBHelper DB;
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
                String user = username.getText().toString();
                String pass = password.getText().toString();

                DB = new DBHelper(Login.this);
                Boolean testMatch = DB.userPasswordMatch(user,pass);

                if (testMatch) {
                    String fname = DB.getFirstName(user);
                    String lname = DB.getLastName(user);
                    String pronouns = DB.getPronouns(user);

                    CurrentUser.init(user, fname, lname, pronouns);
                    Toast.makeText(Login.this,"Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }else{
                    Toast.makeText(Login.this,"Account not found or wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}
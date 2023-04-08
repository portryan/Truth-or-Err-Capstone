package com.portryan.truthorerrcapstone.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.portryan.truthorerrcapstone.DBHelper;
import com.portryan.truthorerrcapstone.R;

public class Signup extends AppCompatActivity {
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Sign Up");

        EditText username = (EditText) findViewById(R.id.signup_username);
        EditText password = (EditText) findViewById(R.id.signup_password);
        EditText fname = (EditText) findViewById(R.id.signup_firstname);
        EditText lname = (EditText) findViewById(R.id.signup_lastname);
        EditText pronouns = (EditText) findViewById(R.id.signup_pronouns);
        Button submitButton = (Button) findViewById(R.id.submit_button);
        Button backToLogin = (Button)findViewById(R.id.backToLogin_button);

        DB = new DBHelper(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameTXT = username.getText().toString();
                String passwordTXT = password.getText().toString();
                String fnameTXT = fname.getText().toString();
                String lnameTXT = lname.getText().toString();
                String pronounsTXT = pronouns.getText().toString();

                //TODO - Hash password

                boolean checkAdd;
                if (usernameTXT.length() > 0 && passwordTXT.length() > 0 && fnameTXT.length() > 0 && lnameTXT.length() > 0 && pronounsTXT.length() > 0){
                    checkAdd = DB.addUser(usernameTXT,passwordTXT,fnameTXT,lnameTXT,pronounsTXT);
                }else{
                    checkAdd = false;
                }

                if (checkAdd){
                    Toast.makeText(Signup.this,"Account Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this, Login.class);
                    startActivity(intent);
                    finishAffinity();

                }else{
                    Toast.makeText(Signup.this,"Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
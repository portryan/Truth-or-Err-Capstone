package com.portryan.truthorerrcapstone.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.portryan.truthorerrcapstone.CurrentUser;
import com.portryan.truthorerrcapstone.DBHelper;
import com.portryan.truthorerrcapstone.R;

public class Profile extends AppCompatActivity {


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_friends:
                intent = new Intent(this, Friends.class);
                startActivity(intent);
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("My Profile");

        TextView username = (TextView)findViewById(R.id.username);
        TextView points = (TextView)findViewById(R.id.points);
        username.setText(CurrentUser.getUsername());
        points.setText("Points: " + Integer.toString(CurrentUser.getPoints()));

        Button logoutButton = (Button)findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUser.reset();
                Intent intent = new Intent(Profile.this, Login.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        Button deleteAccountButton = (Button)findViewById(R.id.deleteOrDeactivateAccount);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper DB = new DBHelper(Profile.this);
                DB.deleteUser(CurrentUser.getUsername());
                CurrentUser.reset();
                Intent intent = new Intent(Profile.this, Login.class);
                startActivity(intent);
                finishAffinity();
            }
        });


    }
}
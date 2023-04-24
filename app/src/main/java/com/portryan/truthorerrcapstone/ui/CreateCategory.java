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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.portryan.truthorerrcapstone.CurrentUser;
import com.portryan.truthorerrcapstone.DBHelper;
import com.portryan.truthorerrcapstone.R;
import com.portryan.truthorerrcapstone.databinding.ActivityLoginBinding;

public class CreateCategory extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intent = new Intent(this, Profile.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
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
        setContentView(R.layout.activity_create_category);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Create Category");

        EditText categoryField = (EditText) findViewById(R.id.text_categoryName);
        Button submitButton = (Button) findViewById(R.id.category_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper DB = new DBHelper(CreateCategory.this);
                String categoryName = categoryField.getText().toString();
                boolean checkAdd;
                if (categoryName.length() > 0) {
                    checkAdd = DB.addCategory(categoryName, CurrentUser.getUsername());
                }else{
                    checkAdd = false;
                }

                if (checkAdd) {
                    Toast.makeText(CreateCategory.this, "Category Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateCategory.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }else{
                    Toast.makeText(CreateCategory.this, "Category Already Exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
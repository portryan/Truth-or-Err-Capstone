package com.portryan.truthorerrcapstone.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.portryan.truthorerrcapstone.R;

public class CreateQuestion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Create Question");

        Spinner correctAnswerSpinner = (Spinner)findViewById(R.id.correct_dropdown);
        String[] ansNums = getResources().getStringArray(R.array.correctAnswerNumbers);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, ansNums);
        correctAnswerSpinner.setPrompt("TEst");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
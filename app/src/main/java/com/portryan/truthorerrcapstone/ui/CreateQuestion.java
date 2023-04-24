package com.portryan.truthorerrcapstone.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.portryan.truthorerrcapstone.CurrentUser;
import com.portryan.truthorerrcapstone.DBHelper;
import com.portryan.truthorerrcapstone.R;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestion extends AppCompatActivity{

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
        setContentView(R.layout.activity_create_question);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Create Question");
        Spinner selectCategorySpinner = (Spinner)findViewById(R.id.category_dropdown);

        List<String> categories = new ArrayList<String>();
        categories.add("Select Category");
        DBHelper DB = new DBHelper(CreateQuestion.this);
        Cursor list = DB.getCategories();
        while (list.moveToNext()){
            categories.add(list.getString(0));
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategorySpinner.setAdapter(adp);

        selectCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button submitButton = (Button) findViewById(R.id.submitCreateQuestion);
        EditText questionTitle = (EditText) findViewById(R.id.text_question);
        // selectCategorySpinner
        EditText pointsField = (EditText) findViewById(R.id.text_points);
        EditText ans1Text = (EditText) findViewById(R.id.text_answer1);
        EditText ans2Text = (EditText) findViewById(R.id.text_answer2);
        EditText ans3Text = (EditText) findViewById(R.id.text_answer3);
        EditText ans4Text = (EditText) findViewById(R.id.text_answer4);
        Spinner correctAnswerSpinner = (Spinner) findViewById(R.id.correct_dropdown);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int errorCount = 0;
                if (questionTitle.getText().toString().length() < 1){
                    errorCount++;
                }
                if (selectCategorySpinner.getSelectedItemPosition() == 0){
                    errorCount++;
                }
                int points = 0;
                if (pointsField.getText().toString().length() < 1){
                    errorCount++;
                }else{
                    try{
                        points = Integer.parseInt(pointsField.getText().toString());
                        if (points < 0 || points > 500) { // TODO change max point limit?
                            errorCount++;
                        }
                    }catch (Exception e){
                        errorCount++;
                    }
                }
                if (ans1Text.getText().toString().length() < 1){
                    errorCount++;
                }
                if (ans2Text.getText().toString().length() < 1){
                    errorCount++;
                }
                if (ans3Text.getText().toString().length() < 1){
                    errorCount++;
                }
                if (ans4Text.getText().toString().length() < 1){
                    errorCount++;
                }
                if (correctAnswerSpinner.getSelectedItemPosition() == 0){
                    errorCount++;
                }

                if (errorCount == 0){
                    String title = questionTitle.getText().toString();
                    String category =  selectCategorySpinner.getSelectedItem().toString();
                    String ans1 = ans1Text.getText().toString();
                    String ans2 = ans2Text.getText().toString();
                    String ans3 = ans3Text.getText().toString();
                    String ans4 = ans4Text.getText().toString();
                    int correctAns = Integer.parseInt(correctAnswerSpinner.getSelectedItem().toString());
                    boolean checkAdd = DB.addQuestion(CurrentUser.getUsername(), title, category, points, ans1, ans2, ans3, ans4, correctAns);
                    if (checkAdd){
                        Toast.makeText(CreateQuestion.this, "Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CreateQuestion.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(CreateQuestion.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CreateQuestion.this, "Error: Complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.portryan.truthorerrcapstone.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.portryan.truthorerrcapstone.CurrentUser;
import com.portryan.truthorerrcapstone.DBHelper;
import com.portryan.truthorerrcapstone.R;

public class AnswerQuestion extends AppCompatActivity implements View.OnClickListener {
    private int correctAns = 0;
    private int points = 0;
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
        setContentView(R.layout.activity_answer_question);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Answer Question");

        Intent previousIntent = getIntent();
        int questionID = previousIntent.getIntExtra("questionID", 0);

        DBHelper DB = new DBHelper(this);
        Cursor questionInfo = DB.getQuestionByID(questionID);
        questionInfo.moveToFirst();
        String username = questionInfo.getString(1);
        String categoryName = questionInfo.getString(3);
        String questionTitle = questionInfo.getString(2);
        String ans1 = questionInfo.getString(5);
        String ans2 = questionInfo.getString(6);
        String ans3 = questionInfo.getString(7);
        String ans4 = questionInfo.getString(8);
        correctAns = questionInfo.getInt(9);
        points = questionInfo.getInt(4);

        TextView usernameField = (TextView) findViewById(R.id.answerQuestion_name);
        TextView pointsField = (TextView) findViewById(R.id.answerQuestion_points);
        TextView categoryField = (TextView) findViewById(R.id.answerQuestion_categoryName);
        TextView titleField = (TextView) findViewById(R.id.answerQuestion_title);
        Button answer1Button = (Button) findViewById(R.id.answer1_button);
        Button answer2Button = (Button) findViewById(R.id.answer2_button);
        Button answer3Button = (Button) findViewById(R.id.answer3_button);
        Button answer4Button = (Button) findViewById(R.id.answer4_button);

        usernameField.setText(username + "'s Question");
        pointsField.setText("± " + points + " Points");
        categoryField.setText("Category: " + categoryName);
        titleField.setText(questionTitle);
        answer1Button.setText(ans1);
        answer1Button.setOnClickListener(this);
        answer2Button.setText(ans2);
        answer2Button.setOnClickListener(this);
        answer3Button.setText(ans3);
        answer3Button.setOnClickListener(this);
        answer4Button.setText(ans4);
        answer4Button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        boolean correct = false;
        switch(view.getId()){
            case R.id.answer1_button:
                if (correctAns == 1){
                    correct = true;
                }else{
                    correct = false;
                }
                break;
            case R.id.answer2_button:
                if (correctAns == 2){
                    correct = true;
                }else{
                    correct = false;
                }
                break;
            case R.id.answer3_button:
                if (correctAns == 3){
                    correct = true;
                }else{
                    correct = false;
                }
                break;
            case R.id.answer4_button:
                if (correctAns == 4){
                    correct = true;
                }else{
                    correct = false;
                }
                break;
        }

        if (correct){
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
            points = 0 - points;
        }
        CurrentUser.updatePoints(points);
        DBHelper DB = new DBHelper(this);
        DB.updatePoints(CurrentUser.getUsername());

        // TODO mark question complete in DB

        Intent intent = new Intent(AnswerQuestion.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
package com.portryan.truthorerrcapstone.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.portryan.truthorerrcapstone.DBHelper;
import com.portryan.truthorerrcapstone.R;

public class ListQuestions extends AppCompatActivity {

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Questions List");

        LinearLayout LL = (LinearLayout) findViewById(R.id.listQuestions_LL);

        DBHelper DB = new DBHelper(this);
        Cursor list = DB.getQuestions(); // TODO exclude questions already answered
        int qCount = list.getCount();
        TextView[] questionsArray = new TextView[qCount];
        int count = 0;
        while (list.moveToNext()){
            String title = list.getString(2);
            String category = list.getString(3);
            String author = list.getString(1);
            int points = list.getInt(4);
            int questionID = list.getInt(0);
            TextView tv = new TextView(this);
            tv.setText("Title: " + title + "\nCategory: " + category + "\nPoints: " + points + "\nAuthor: " + author + "\n");
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(24);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            tv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListQuestions.this, AnswerQuestion.class);
                    intent.putExtra("questionID", questionID);
                    startActivity(intent);
                }
            });
            questionsArray[count] = tv;
            count++;
        }

        for (int i = 0; i < qCount; i++){
            LL.addView(questionsArray[i]);
        }




    }
}
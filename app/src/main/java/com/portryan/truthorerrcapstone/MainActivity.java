package com.portryan.truthorerrcapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchText = (EditText)findViewById(R.id.searchText);
        Button createQuestionBtn = (Button)findViewById(R.id.createQuestionBtn);
        Button answerQuestionBtn = (Button)findViewById(R.id.answerQuestionBtn);
        Button createCategoryBtn = (Button)findViewById(R.id.createCategoryBtn);
        Button categoriesBtn = (Button)findViewById(R.id.categoriesBtn);

        createQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateQuestion.class);
                startActivity(intent);
            }
        });

        answerQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AnswerQuestion.class);
                startActivity(intent);
            }
        });

        createCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateCategory.class);
                startActivity(intent);
            }
        });

        categoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Categories.class);
                startActivity(intent);
            }
        });


    }
}
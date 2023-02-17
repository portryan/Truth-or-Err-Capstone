package com.portryan.truthorerrcapstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        TextView TV1 = (TextView) findViewById(R.id.textView1);
        TextView TV2 = (TextView) findViewById(R.id.textView2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String text1 = TV1.getText().toString();
                String text2 = TV2.getText().toString();

                TV1.setText(text2);
                TV2.setText(text1);


            }
        });
    }
}
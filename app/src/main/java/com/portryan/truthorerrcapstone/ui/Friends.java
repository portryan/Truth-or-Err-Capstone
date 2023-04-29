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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.portryan.truthorerrcapstone.CurrentUser;
import com.portryan.truthorerrcapstone.DBHelper;
import com.portryan.truthorerrcapstone.R;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {

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
        setContentView(R.layout.activity_friends);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Friends");

        updateFriends(CurrentUser.getId());

        EditText friendNameField = (EditText) findViewById(R.id.addUsername_text);
        Button addButton = (Button) findViewById(R.id.addFriend_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper DB = new DBHelper(Friends.this);
                String friendName = friendNameField.getText().toString();
                int currentId = CurrentUser.getId();
                int friendID = DB.getUserId(friendName);
                if (friendID == -1){
                    Toast.makeText(Friends.this, "No User Found", Toast.LENGTH_SHORT).show();
                }else if(friendID == currentId){
                    Toast.makeText(Friends.this, "Cannot add yourself", Toast.LENGTH_SHORT).show();
                }else{
                    int minId = Math.min(currentId, friendID);
                    int maxId = Math.max(currentId, friendID);
                    boolean result = DB.addFriends(minId, maxId);
                    if (result) {
                        Toast.makeText(Friends.this, "Added!", Toast.LENGTH_SHORT).show();
                        updateFriends(CurrentUser.getId());
                    }else{
                        Toast.makeText(Friends.this, "Already Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void updateFriends(int currentUserId){
        List<String> friendsNames = new ArrayList<String>();
        DBHelper DB = new DBHelper(this);
        Cursor cursor = DB.getFriends(currentUserId);
        while(cursor.moveToNext()){
            int friendId = cursor.getInt(0);
            String friendName = DB.getUserById(friendId);
            friendsNames.add(friendName);
        }
        LinearLayout LL = (LinearLayout) findViewById(R.id.friendsList_layout);
        LL.removeAllViews();

        if (friendsNames.size() == 0){
            TextView tv = new TextView(this);
            tv.setText("\nNo friends :(");
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(24);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LL.addView(tv);
        }else {
            for (int i = 0; i < friendsNames.size(); i++) {
                Button b = new Button(this);
                b.setText(friendsNames.get(i));
                b.setTextColor(Color.WHITE);
                b.setBackgroundTintList(this.getResources().getColorStateList(R.color.lightPurple));
                b.setLayoutParams(new LinearLayout.LayoutParams(1000, LinearLayout.LayoutParams.WRAP_CONTENT));
                b.setTransformationMethod(null);
                LL.addView(b);
            }
        }
    }
}
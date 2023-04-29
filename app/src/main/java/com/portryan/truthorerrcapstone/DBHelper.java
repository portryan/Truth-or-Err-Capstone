package com.portryan.truthorerrcapstone;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.MediaSessionManager;

import androidx.annotation.Nullable;
import androidx.core.database.sqlite.SQLiteDatabaseKt;

import com.portryan.truthorerrcapstone.ui.Categories;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "Data.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table Users(id INTEGER primary key autoincrement, username TEXT, password TEXT, firstname TEXT, lastname Text, pronouns TEXT, points INTEGER)");
        DB.execSQL("create table Categories(name TEXT primary key, username TEXT, foreign key (username) references Users(username))");
        DB.execSQL("create table Questions(id INTEGER primary key autoincrement, username TEXT, title TEXT, category TEXT, points INTEGER, answer1 TEXT, answer2 TEXT, answer3 TEXT, answer4 TEXT, correctanswer INTEGER, foreign key (username) references Users(username), foreign key (category) references Categories(name))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists Users");
        DB.execSQL("drop table if exists Categories");
        DB.execSQL("drop table if exists Questions");
    }

    public boolean addUser(String username, String pass, String fname, String lname, String pronouns){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",pass);
        contentValues.put("firstname",fname);
        contentValues.put("lastname",lname);
        contentValues.put("pronouns",pronouns);
        contentValues.put("points", 1000);
        long result = DB.insert("Users",null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteUser(String username){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Users where username = ?", new String[]{username});
        if (cursor.getCount() > 0){
            long result = DB.delete("Users","username=?",new String[]{username});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    @SuppressLint("Range")
    public boolean userPasswordMatch(String username, String password){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select password from Users where username = ?",new String[]{username});
        cursor.moveToFirst();
        if (cursor.getCount() < 1){
            return false;
        }
        if (cursor.getString(cursor.getColumnIndex("password")).equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    @SuppressLint("Range")
    public int getUserId(String username){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select id from Users where username = ?",new String[]{username});
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("id"));
    }

    @SuppressLint("Range")
    public String getFirstName(String username){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select firstname from Users where username = ?",new String[]{username});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("firstname"));
    }

    @SuppressLint("Range")
    public String getLastName(String username){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select lastname from Users where username = ?",new String[]{username});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("lastname"));
    }

    @SuppressLint("Range")
    public String getPronouns(String username){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select pronouns from Users where username = ?",new String[]{username});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("pronouns"));
    }

    @SuppressLint("Range")
    public int getPoints(String username){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select points from Users where username = ?",new String[]{username});
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("points"));
    }

    @SuppressLint("Range")
    public boolean updatePoints(String username){
        int pts = CurrentUser.getPoints();
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("points",pts);
        Cursor cursor = DB.rawQuery("Select points from Users where username = ?", new String[]{username});
        if (cursor.getCount() > 0){
            long result = DB.update("Users", contentValues, "username=?", new String[]{username});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }

    }

    public boolean addCategory(String categoryName, String user){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",categoryName);
        contentValues.put("username",user);
        long result = DB.insert("Categories",null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getCategories(){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Categories",null);
        return cursor;
    }

    public boolean addQuestion(String user, String title, String category, int points, String ans1, String ans2, String ans3, String ans4, int correctAns){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("title",title);
        contentValues.put("category", category);
        contentValues.put("points", points);
        contentValues.put("answer1", ans1);
        contentValues.put("answer2", ans2);
        contentValues.put("answer3", ans3);
        contentValues.put("answer4", ans4);
        contentValues.put("correctanswer", correctAns);
        long result = DB.insert("Questions", null, contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getQuestions(){ // TODO exclude questions already answered
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Questions", null);
        return cursor;
    }

    public Cursor getQuestionByID(int qID){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Questions where id = ?", new String[]{Integer.toString(qID)});
        return cursor;
    }
}


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
        DB.execSQL("create table Users(username TEXT primary key, password TEXT, firstname TEXT, lastname Text, pronouns TEXT)");
        DB.execSQL("create table Categories(name TEXT primary key, username TEXT, foreign key (username) references Users(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists Users");
        DB.execSQL("drop table if exists Categories");
    }

    public boolean addUser(String username, String pass, String fname, String lname, String pronouns){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",pass);
        contentValues.put("firstname",fname);
        contentValues.put("lastname",lname);
        contentValues.put("pronouns",pronouns);
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
}


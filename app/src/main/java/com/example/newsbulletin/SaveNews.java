package com.example.newsbulletin;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class SaveNews extends SQLiteOpenHelper {

    public static final String Database_name = "NewsBulletin.db";
    public static final String Table_name = "offline_news";
    public static final String COL_2 = "Image_src";
    public static final String COL_3 = "Source_logo";
    public static final String COL_4 = "Publisher";
    public static final String COL_5 = "Title";
    public static final String COL_6 = "Date";
    public static final String COL_7 = "Saved_date";
    public static final String COL_8 = "Link";


    public SaveNews(@Nullable Context context) {
        super(context, Database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table_name + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Image_src TEXT UNIQUE, Source_logo TEXT, Publisher TEXT UNIQUE, Title TEXT, " +
                "Date TEXT, Saved_date TEXT, Link TEXT)");
        db.execSQL("CREATE TABLE recommendation (Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category UNIQUE, value INTEGER)");
        db.execSQL("INSERT INTO recommendation (category, value ) VALUES('nepal', 0)");
        db.execSQL("INSERT INTO recommendation (category, value ) VALUES('world', 0)");
        db.execSQL("INSERT INTO recommendation (category, value ) VALUES('sports', 0)");
        db.execSQL("INSERT INTO recommendation (category, value ) VALUES('tech', 0)");
        db.execSQL("INSERT INTO recommendation (category, value ) VALUES('entertainment', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        db.execSQL("DROP TABLE IF EXISTS recommendation");
    }

    public boolean insertData(String Img_src, String Source_logo, String Title, String Publisher, String Date, String Saved_date, String Link){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, Img_src);
        contentValues.put(COL_3, Source_logo);
        contentValues.put(COL_4, Title);
        contentValues.put(COL_5, Publisher);
        contentValues.put(COL_6, Date);
        contentValues.put(COL_7, Saved_date);
        contentValues.put(COL_8, Link);
        long result = db.insert(Table_name, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT *FROM " + Table_name, null);
    }

    public void deleteData(String Img_src){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_name, "Image_src= ?", new String[] {Img_src});
    }

    public Cursor getParticularData(String image){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT *FROM " + Table_name + " WHERE Img_src="+ image, null);
    }

    public void updateScore(String name, int point) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", point);
        db.update("recommendation", contentValues,"category=?", new String[]{name});
    }

    public Cursor getRecommended() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT *FROM recommendation", null);
    }

}

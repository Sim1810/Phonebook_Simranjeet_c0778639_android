package com.example.phonebook_simranjeet_c0778639_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ContactDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "person";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FNAME = "fname";
    private static final String COLUMN_LNAME = "lname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE= "phone";
    private static final String COLUMN_ADDRESS = "address";

    public DataBase(@Nullable Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT person_pk PRIMARY KEY AUTOINCREMENT, " +

                COLUMN_FNAME + " varchar(200) NOT NULL, " +
                COLUMN_LNAME + " varchar(200) NOT NULL, " +
                COLUMN_EMAIL + " varchar(200) NOT NULL, " +

                COLUMN_PHONE + " varchar(200) NOT NULL, " +
                COLUMN_ADDRESS + " varchar(300) NOT NULL); ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);

    }

    boolean addContact(String fname, String lname, String email, String phone, String address ) {

        //this method returns a sqlite instance;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //contai value object
        ContentValues cv = new ContentValues();
        //this first argument of the put method is the columnn name and second value

        cv.put(COLUMN_FNAME,lname);
        cv.put(COLUMN_LNAME,fname);
        cv.put(COLUMN_EMAIL,email);
        cv.put(COLUMN_PHONE,phone);
        cv.put(COLUMN_ADDRESS,address);
        //insert returns vallue of rownumber and -1 is not sucessfull ;

        return  sqLiteDatabase.insert(TABLE_NAME,null,cv)!= 1;

    }

    Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase =getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);

    }

    boolean updateContactData(int id,String fname,String lname, String email,String phone,String address){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        //this first argument of the put method is the columnn name and second value

        cv.put(COLUMN_FNAME,lname);
        cv.put(COLUMN_LNAME,fname);
        cv.put(COLUMN_EMAIL,email);
        cv.put(COLUMN_PHONE,phone);
        cv.put(COLUMN_ADDRESS,address);

        //returns the affected num of rows;
        return  sqLiteDatabase.update(TABLE_NAME,cv,COLUMN_ID+" = ? ",new String[]{String.valueOf(id)}) >0 ;
    }

    boolean deletePerson(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_NAME,COLUMN_ID+" = ? ",new String[]{String.valueOf(id)}) >0;

    }



}








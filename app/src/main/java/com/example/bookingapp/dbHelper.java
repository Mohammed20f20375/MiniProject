package com.example.bookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "booking.db";
    public static final String TABLE_NAME = "bookings";
    public static final String COL_1 = "CUSTOMER_NAME";
    public static final String COL_2 = "MOBILE_NUMBER";
    public static final String COL_3 = "NUMBER_OF_GUESTS";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "TIME";

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CUSTOMER_NAME TEXT, MOBILE_NUMBER TEXT, NUMBER_OF_GUESTS TEXT, DATE TEXT, TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String customerName, String mobileNumber, String numberOfGuests, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, customerName);
        contentValues.put(COL_2, mobileNumber);
        contentValues.put(COL_3, numberOfGuests);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, time);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
package com.changed.supun.kitchenmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database name
    public static final String DATABASE_NAME = "kitchenitems.db";

    //table name
    public static final String TABLE_NAME = "products_table";


    //columns of the table
    public static final String COL_1 = "_id";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "WEIGHT";
    public static final String COL_4 = "PRICE";
    public static final String COL_5 = "DESCRIPTION";
    public static final String COL_6 = "AVAILABILITY";
    public static final String KEY_ROWID = "_id";

    //Constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    //database creation method
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" create table " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, WEIGHT REAL, PRICE REAL, DESCRIPTION TEXT,AVAILABILITY INTEGER)");

    }

    //database upgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    //inserting data in to the database
    public boolean insertData(String name, double weight, double price, String description, int availability) {

        //create the database and the table
        SQLiteDatabase db = this.getWritableDatabase();

        //Content Values
        ContentValues contentValues = new ContentValues();

        //inserting data to table
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, weight);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, description);
        contentValues.put(COL_6, availability);

        //checking the result
        long response = db.insert(TABLE_NAME, null, contentValues);

        if (response == -1) {
            return false;
        } else {
            return true;
        }
    }

    //method to get all data ordered in the ascending order (ID) used by edit products
    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from products_table ORDER BY _ID ASC ", null);
        return res;

    }

    //method to get all data ordered in the ascending order ( Used by Display Products)
    public Cursor getAllDataAlphabetical() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from products_table ORDER BY NAME ASC ", null);
        return res;

    }


    //method to update only the availability
    public boolean updateAvailability(int availability, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COL_6, availability);

        db.update(TABLE_NAME, contentValues, "_ID = ?", new String[]{id});

        return true;
    }

    //method to get only checked data
    public Cursor getOnlyCheckedData() {

        SQLiteDatabase db = this.getWritableDatabase();

        //query
        Cursor res = db.rawQuery("select * from products_table WHERE AVAILABILITY = 1  ORDER BY NAME ASC ", null);
        return res;
    }

    //method to update all data
    public boolean updateAllData(String id, String name, double weight, double price, String description, int availability) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, weight);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, description);
        contentValues.put(COL_6, availability);

        db.update(TABLE_NAME, contentValues, "_ID = ?", new String[]{id});

        return true;
    }


    //search by keyword ( method used for the search function)
    public Cursor getKitchenItemListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  rowid as " +
                COL_1 + "," +
                COL_2 + "," +
                COL_3 + "," +
                COL_4 + "," +
                COL_5 + "," +
                COL_6 +
                " FROM " + TABLE_NAME +
                " WHERE " + COL_2 + " LIKE  '%" + search + "%' OR " + COL_5 + " LIKE '%" + search + "%'";


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;

    }


}

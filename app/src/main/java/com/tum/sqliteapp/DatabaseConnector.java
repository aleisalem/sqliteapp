package com.tum.sqliteapp;

/**
 * Created by aesalem on 30.10.16.
 * Based on the class from "com.attify.sqliteapp" by Aditya Gupta (@adi1391)
 */

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector extends SQLiteOpenHelper {
    private static final int Version = 1;
    private static final String databaseName = "sqliteapp";
    private static final String email = "EMAIL";
    private static final String firstName = "FIRST_NAME";
    private static final String id = "ID";
    private static final String lastName = "LAST_NAME";
    private static final String password = "PASSWORD";
    private static final String phoneNumber = "PHONE_NUMBER";
    private static final String tableName = "USERS";
    private static final String username = "USERNAME";

    public DatabaseConnector(Context context) {
        super(context, databaseName, null, Version);
    }

    public void onCreate(SQLiteDatabase database) {
            String createTableSQL = "CREATE TABLE USERS (ID INTEGER NOT NULL PRIMARY KEY, FIRST_NAME TEXT, LAST_NAME TEXT, EMAIL TEXT, PHONE_NUMBER TEXT, USERNAME TEXT, PASSWORD TEXT)";
            Log.d("onCreate()", createTableSQL);
            database.execSQL(createTableSQL);
    }

    public void onUpgrade(SQLiteDatabase database, int arg1, int arg2) {
    }

    public void addRecord(String firstname, String lastname, String emailAddress, String phone, String uname, String pword) {
        String insertSQL = "INSERT INTO USERS (FIRST_NAME, LAST_NAME ,EMAIL ,PHONE_NUMBER ,USERNAME ,PASSWORD) VALUES ('" + firstname + "', '" + lastname + "', '" + emailAddress + "', '" + phone + "', '" + uname + "', '" + pword + "')";
        Log.d("addRecord()", insertSQL);
        SQLiteDatabase dataBase = getWritableDatabase();
        dataBase.execSQL(insertSQL);
        dataBase.close();
    }

    public List<String> getRecord(String uname, String pword) {
        List<String> recordList = new ArrayList();
        SQLiteDatabase dataBase = getReadableDatabase();
        String getSQL = "SELECT * FROM USERS WHERE USERNAME LIKE '" + uname + "' AND " + password + " = '" + pword + "'";
        Cursor cursor = dataBase.rawQuery(getSQL, null);
        Log.d("getRecord()", new StringBuilder(String.valueOf(getSQL)).append("##Count = ").append(cursor.getCount()).toString());
        cursor.moveToFirst();
        String fName = cursor.getString(Version);
        String lName = cursor.getString(2);
        String eMail = cursor.getString(3);
        String ph = cursor.getString(4);
        Log.d("getRecord()", "FirstName: " + fName + "LastName: " + lName + "Email: " + eMail + "Phone" + ph);
        recordList.add(fName);
        recordList.add(lName);
        recordList.add(eMail);
        recordList.add(ph);
        dataBase.close();
        return recordList;
    }
}


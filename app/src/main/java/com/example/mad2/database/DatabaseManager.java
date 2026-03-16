package com.example.mad2.database;

import android.content.Context;

import com.example.mad2.activity.MainActivity;

public class DatabaseManager {
    private static DatabaseHelper databaseHelper;

//    public static void init(MainActivity context) {
    public static void init(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
    }

    public static DatabaseHelper getInstance() {
        return databaseHelper;
    }
}
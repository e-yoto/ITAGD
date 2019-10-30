package edu.itagd.tgnamo.istodayagooddayto.controller.database.activity;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ActivityDBOpenHelper extends SQLiteAssetHelper {
    //Name of the database.
    private  static final String DATABASE_NAME = "activities.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Access the database.
     */
    public ActivityDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}

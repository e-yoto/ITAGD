package edu.itagd.tgnamo.istodayagooddayto.controller.database.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ActivityDBAccessController {
    private SQLiteOpenHelper helper;
    private SQLiteDatabase database;
    private static ActivityDBAccessController instance;

    /**
     * Constructor of the access controller.
     */
    private ActivityDBAccessController(Context cont) {
        this.helper = new ActivityDBOpenHelper(cont);
    }

    /**
     * Creates an instance of the access controller.
     * @param cont
     * @return an instance of the access controller
     */
    public static synchronized ActivityDBAccessController getInstance(Context cont) {
        if (instance == null) {
            instance = new ActivityDBAccessController(cont);
        }
        return instance;
    }

    /**
     * Open the database.
     */
    public SQLiteDatabase openDatabase() {
        this.database = helper.getWritableDatabase();
        return this.database;
    }

    /**
     * Close the database.
     */
    public void closeDatabase() {
        if (database != null) {
            this.database.close();
        }
    }
}

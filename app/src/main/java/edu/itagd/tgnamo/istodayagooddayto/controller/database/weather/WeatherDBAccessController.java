package edu.itagd.tgnamo.istodayagooddayto.controller.database.weather;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDBAccessController {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static WeatherDBAccessController accessController;

    private WeatherDBAccessController(SQLiteOpenHelper openHelper){
        this.openHelper = openHelper;
    }

    public static synchronized WeatherDBAccessController getAccessController(SQLiteOpenHelper openHelper){
        if (accessController == null){
            accessController = new WeatherDBAccessController(openHelper);
        }

        return accessController;
    }

    public SQLiteDatabase openDatabase(){
        this.database = openHelper.getWritableDatabase();
        return database;
    }

    public void closeDatabase(){
        if (database != null){
            this.database.close();
        }
    }
}

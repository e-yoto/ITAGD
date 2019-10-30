package edu.itagd.tgnamo.istodayagooddayto.controller.database.weather;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class WeatherDBOpenHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "weather.db";
    private static final int DB_VERSION = 1;

    public WeatherDBOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
}

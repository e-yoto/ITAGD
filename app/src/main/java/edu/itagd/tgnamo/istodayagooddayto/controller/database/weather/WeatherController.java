package edu.itagd.tgnamo.istodayagooddayto.controller.database.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import edu.itagd.tgnamo.istodayagooddayto.model.Weather;

public class WeatherController {
    private WeatherDBAccessController accessController;
    private SQLiteDatabase database;
    private final String DB_NAME = "weather";
    private final String TAG = this.getClass().getSimpleName();

    //Each weather has its own ID from the API to identify each weather,
    //it is not the id to identify rows in the database
    private final String WEATHER_ID = "id";
    private final String WEATHER = "weather";
    private final String DESCRIPTION = "description";
    private final String TEMPERATURE = "temperature";
    private final String HUMIDITY = "humidity";
    private final String PRESSURE = "pressure";
    private final String SUNRISE = "sunrise";
    private final String SUNSET = "sunset";
    private final String CITY = "city";
    private final String COUNTRY = "country";

    public WeatherController(Context context){
        WeatherDBOpenHelper openHelper = new WeatherDBOpenHelper(context);
        this.accessController = WeatherDBAccessController.getAccessController(openHelper);
    }

    public Weather getWeather(){
        database = this.accessController.openDatabase();
        Weather theWeather = null;

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + DB_NAME, null);
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String weather = cursor.getString(cursor.getColumnIndex("weather"));
            String weatherDesc = cursor.getString(cursor.getColumnIndex("description"));
            long temperature = cursor.getLong(cursor.getColumnIndex("temperature"));
            String humidity = cursor.getString(cursor.getColumnIndex("humidity"));
            String pressure = cursor.getString(cursor.getColumnIndex("pressure"));
            String sunrise = cursor.getString(cursor.getColumnIndex("sunrise"));
            String sunset = cursor.getString(cursor.getColumnIndex("sunset"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String country = cursor.getString(cursor.getColumnIndex("country"));
            theWeather = new Weather(id, weather, weatherDesc, temperature, humidity, pressure, sunrise, sunset, city, country);
            cursor.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            this.accessController.closeDatabase();
        }
        return theWeather;
    }

    public void createWeather(Weather weather){
        database = this.accessController.openDatabase();

        Cursor cursor = database.rawQuery("SELECT count(*) FROM " + "'"+DB_NAME+"'", null);
        cursor.moveToFirst();
        //If table exists already, it will not create a new one.
        //Instead it will call the update method to update the current row.
        if (cursor.getInt(0) > 0){
            updateWeather(weather);
            Log.d(TAG, "Weather will not be newly created. Updated recent one instead.");
            this.accessController.closeDatabase();
            return;
        }
        cursor.close();

        database.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", weather.getId());
            contentValues.put("weather", weather.getWeather());
            contentValues.put("description", weather.getWeatherDesc());
            contentValues.put("temperature", weather.getTemperature());
            contentValues.put("humidity", weather.getHumidity());
            contentValues.put("pressure", weather.getPressure());
            contentValues.put("sunrise", weather.getSunrise());
            contentValues.put("sunset", weather.getSunset());
            contentValues.put("city", weather.getCity());
            contentValues.put("country", weather.getCountry());
            database.setTransactionSuccessful();
            database.insert(DB_NAME, null, contentValues);
            Log.d(TAG, "Weather created");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            database.endTransaction();
            this.accessController.closeDatabase();
        }
    }

    public void updateWeather(Weather weather){
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", weather.getId());
            contentValues.put("weather", weather.getWeather());
            contentValues.put("description", weather.getWeatherDesc());
            contentValues.put("temperature", weather.getTemperature());
            contentValues.put("humidity", weather.getHumidity());
            contentValues.put("pressure", weather.getPressure());
            contentValues.put("sunrise", weather.getSunrise());
            contentValues.put("sunset", weather.getSunset());
            contentValues.put("city", weather.getCity());
            contentValues.put("country", weather.getCountry());
            String whereClause = "rowid = 1";
            database.update(DB_NAME, contentValues, whereClause, null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}

package edu.itagd.tgnamo.istodayagooddayto.controller.database.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.itagd.tgnamo.istodayagooddayto.model.Activity;

public class ArchivesController {
    private ActivityDBAccessController databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String TABLE_NAME = "archive";
    private static final String TAG = ActivityController.class.getSimpleName();

    /**
     * Constructor for a controller to manage interactions between the views
     * context and the Activity model.
     * @param cont
     */
    public ArchivesController(Context cont) {
        this.databaseHelper = ActivityDBAccessController.getInstance(cont);
    }

    /**
     * Gets all the archived activities to the database and returns it as an ArrayList.
     * @return all archived activities
     */
    public List<Activity> getAllArchives() {
        List<Activity> activities = new ArrayList<>();

        sqLiteDatabase = this.databaseHelper.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Activity activity = new Activity();
                activity.setActivity(cursor.getString(cursor.getColumnIndex("name")));
                activity.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                activity.setMinute(cursor.getString(cursor.getColumnIndex("minute")));
                activity.setWeather(cursor.getString(cursor.getColumnIndex("weather")));
                activity.setIsMorning(cursor.getInt(cursor.getColumnIndex("morning")));

                activities.add(activity);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.databaseHelper.closeDatabase();
        }
        return activities;
    }

    /**
     * Add a new archived activity to the databse.
     * @param newArchive archive to be added
     */
    public void createArchive(Activity newArchive) {
        List<Activity> archives = getAllArchives();

        sqLiteDatabase = this.databaseHelper.openDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            //Add values to the database table.
            ContentValues values = new ContentValues();
            values.put("name", newArchive.getActivity());
            values.put("hour", newArchive.getHour());
            values.put("minute", newArchive.getMinute());
            values.put("weather", newArchive.getWeather());
            values.put("morning", newArchive.getIsMorning());

            sqLiteDatabase.insert(TABLE_NAME, TAG, values);
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e) {
            Log.w(TAG, e.fillInStackTrace());
        } finally {

            sqLiteDatabase.endTransaction();
            this.databaseHelper.closeDatabase();
        }
    }

    /**
     * Delete an archived activity from the database based on its name.
     * @param activity the archived activity to be deleted
     */
    public void deleteArchive(Activity activity) {
        sqLiteDatabase = this.databaseHelper.openDatabase();
        try {
            //Delete activity from the databse
            sqLiteDatabase.delete(TABLE_NAME, "name = ?", new String[]{activity.getActivity()});
        }

        catch (Exception e) {
            Log.w(TAG, e.fillInStackTrace());
        }
        finally {
            sqLiteDatabase.close();
            this.databaseHelper.closeDatabase();
        }
    }
}

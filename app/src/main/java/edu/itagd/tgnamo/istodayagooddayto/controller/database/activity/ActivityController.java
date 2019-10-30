package edu.itagd.tgnamo.istodayagooddayto.controller.database.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.itagd.tgnamo.istodayagooddayto.model.Activity;

public class ActivityController {
    private ActivityDBAccessController databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String TABLE_NAME = "activity";
    private static final String TAG = ActivityController.class.getSimpleName();

    /**
     * Constructor for a controller to manage interactions between the views
     * context and the Activity model.
     * @param cont
     */
    public ActivityController(Context cont) {
        this.databaseHelper = ActivityDBAccessController.getInstance(cont);
    }

    /**
     * Gets all the activities to the database and returns it as an ArrayList.
     * @return all activities
     */
    public List<Activity> getAllActivities() {
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
     * Add a new activity to the databse.
     * @param createdActivity activity to be added
     */
    public void createActivity(Activity createdActivity) {
        List<Activity> activities = getAllActivities();

        sqLiteDatabase = this.databaseHelper.openDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            //Add values to the database table.
            ContentValues values = new ContentValues();
            values.put("name", createdActivity.getActivity());
            values.put("hour", createdActivity.getHour());
            values.put("minute", createdActivity.getMinute());
            values.put("weather", createdActivity.getWeather());
            values.put("morning", createdActivity.getIsMorning());

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
     * Delete an activity from the database based on its name.
     * @param activity the activityto be deleted
     */
    public void deleteActivity(Activity activity) {
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

    /**
     * Update an activity in the database based on its name
     * @param prevActivity activity containing the old information
     * @param newActivity activity that will replace with new information
     */
    public void updateActivity(Activity prevActivity, Activity newActivity) {
        sqLiteDatabase = this.databaseHelper.openDatabase();

        try {
            //Update values in the database table.
            ContentValues values = new ContentValues();
            values.put("name", newActivity.getActivity());
            values.put("hour", newActivity.getHour());
            values.put("minute", newActivity.getMinute());
            values.put("weather", newActivity.getWeather());
            values.put("morning", newActivity.getIsMorning());

            sqLiteDatabase.update(TABLE_NAME, values, "name = ?", new String[]{prevActivity.getActivity()});
        }
        catch (Exception e) {
            Log.w(TAG, e.fillInStackTrace());
        }
        finally {
            databaseHelper.closeDatabase();
        }
    }
}

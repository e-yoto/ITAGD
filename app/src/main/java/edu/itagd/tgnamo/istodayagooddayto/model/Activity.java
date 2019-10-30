package edu.itagd.tgnamo.istodayagooddayto.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity implements Parcelable{
    //Name of the activity
    String activity;

    //Time when the user should do the activity (activity conditions)
    String hour;
    String minute;
    boolean morning;
    int isMorning = 0;

    //Weather where the user should do the activity.
    String weather;

    /**
     * Reminder: An activity is the action/event that the user
     * wishes to do, not an activity (screen) of an app.
     */
    public Activity(){
        //empty constructor for database
    }

    /**
     * Returns the activity.
     * @return activity
     */
    public String getActivity() {
        return activity;
    }

    /**
     * Sets the activity.
     * @param activity
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * Returns the activity's hour condition.
     * @return hour
     */
    public String getHour() {
        return hour;
    }

    /**
     * Sets the activity's hour condition.
     * @param hour
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /**
     * Returns the activity's minute condition.
     * @return minute
     */
    public String getMinute() {
        return minute;
    }

    /**
     * Sets the activity's minute condition.
     * @param minute
     */
    public void setMinute(String minute) {
        this.minute = minute;
    }

    /**
     * Returns true or false if the activity should be done in the morning.
     * @return morning
     */
    public boolean getMorning() {
        return morning;
    }

    /**
     * Sets the activity's morning/afternoon condition (true or false).
     * @param morning
     */
    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    /**
     * Returns 0 or 1 if the activity should be done in the morning.
     * For the database.
     * @return isMorning
     */
    public int getIsMorning() {
        return isMorning;
    }

    /**
     * Sets the activity's morning/afternoon condition (0 or 1).
     * For the database.
     * @param isMorning
     */
    public void setIsMorning(int isMorning) {
        this.isMorning = isMorning;
    }

    /**
     * Returns the activity's weather condition.
     * @return weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     * Sets the activity's weather condition.
     * @param weather
     */
    public void setWeather(String weather) {
        this.weather = weather;
    }

    /**
     * Returns the String representation of the Activity.
     * @return string
     */
    @Override
    public String toString() {
        return this.getActivity() + " " + this.getHour() + ":" + this.getMinute() + " " + this.getWeather();
    }

    /**
     * Creates an Activity parcel to send to another activity.
     */
    public static final Creator<Activity> CREATOR = new Creator<Activity>() {

        @Override
        public Activity createFromParcel(Parcel source) {
            return new Activity(source);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[0];
        }
    };

    /**
     * Initializes a parcel to send data to another activity.
     * @param source
     */
    protected Activity(Parcel source) {
        activity = source.readString();
        hour = source.readString();
        minute = source.readString();
        weather = source.readString();
        isMorning = source.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}

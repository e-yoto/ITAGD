package edu.itagd.tgnamo.dataandstyle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class ThemeHelper {
    public static int currentTheme;
    public static final int LIGHT = 0;
    public static final int DARK = 1;

    /**
     * Saves the theme selected (LIGHT OR DARK) and
     * resets the activity to set the theme.
     * @param activity
     * @param theme
     */
    public static void changeTheme(AppCompatActivity activity, int theme){
        currentTheme = theme;
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.finish();
    }

    /**
     * Sets the theme based on the selection of the user.
     * @param activity
     */
    public static void setTheme(AppCompatActivity activity){
        switch(currentTheme){
            case DARK:
                activity.setTheme(R.style.DarkTheme);
                break;
            default:
            case LIGHT:
                activity.setTheme(R.style.AppTheme);
                break;
        }
    }
}

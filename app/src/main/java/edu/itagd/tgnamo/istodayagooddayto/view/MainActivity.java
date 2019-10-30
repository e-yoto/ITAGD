package edu.itagd.tgnamo.istodayagooddayto.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.itagd.tgnamo.dataandstyle.ThemeHelper;
import edu.itagd.tgnamo.istodayagooddayto.R;
import edu.itagd.tgnamo.istodayagooddayto.controller.NotificationReceiver;
import edu.itagd.tgnamo.istodayagooddayto.controller.apicall.APIController;
import edu.itagd.tgnamo.istodayagooddayto.controller.apicall.JSONParser;
import edu.itagd.tgnamo.istodayagooddayto.controller.database.activity.ActivityController;
import edu.itagd.tgnamo.istodayagooddayto.controller.database.weather.WeatherController;
import edu.itagd.tgnamo.istodayagooddayto.model.Activity;
import edu.itagd.tgnamo.istodayagooddayto.model.Weather;
import edu.itagd.tgnamo.istodayagooddayto.view.activities.ActivitiesActivity;
import edu.itagd.tgnamo.istodayagooddayto.view.custom.CustomAlertView;
import edu.itagd.tgnamo.istodayagooddayto.view.custom.CustomTextView;
import edu.itagd.tgnamo.istodayagooddayto.view.suntime.SunActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout background;
    CustomTextView tvTemperature;
    TextView tvDegree;
    TextView tvWeather;
    TextView tvDate;
    TextView tvPlace;
    TextView tvTime;
    CardView cvSunset;
    CardView cvSunrise;
    TextView updatedString;
    ImageView ivSoundCloud;
    FloatingActionButton btnActivities;
    CustomAlertView cvAlert;

    Weather weather;
    int weatherId;
    long temperatureLong;
    String weatherString;
    String weatherDesc;
    String dateString;
    String placeString;
    String timeString;
    String sunsetTime;
    String sunriseTime;
    String humidityString;
    String pressureString;

    final public int PENDING_INTENT_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sets the theme based on the user's selection from the options menu.
        loadTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background = (LinearLayout)findViewById(R.id.lay_background);
        tvTemperature = (CustomTextView) findViewById(R.id.txt_temperature);
        tvDegree = (TextView)findViewById(R.id.txt_degree);
        tvWeather = (TextView)findViewById(R.id.txt_weather);
        tvDate = (TextView)findViewById(R.id.txt_date);
        tvPlace = (TextView) findViewById(R.id.txt_place);
        tvTime = (TextView)findViewById(R.id.txt_time_updated);
        updatedString = (TextView)findViewById(R.id.txt_time);
        cvSunset = (CardView)findViewById(R.id.sunset_card);
        cvSunrise = (CardView)findViewById(R.id.sunrise_card);
        ivSoundCloud = (ImageView)findViewById(R.id.ic_soundcloud);
        btnActivities = (FloatingActionButton) findViewById(R.id.btn_open_activities);
        cvAlert = (CustomAlertView) findViewById(R.id.cv_alert);


        //Sets a context menu on the numerical tvTemperature.
        registerForContextMenu(tvTemperature);


        setResults();

        //Set notification that will send a notification at 7 am
        setNotification();

        //Set styles depending on tvWeather.
        setStyle(weatherId);
        if (!((weatherId >= 600 && weatherId <= 622) || (weatherId >= 801 && weatherId <= 804) ||
                (weatherId >= 701 && weatherId <= 741) || (weatherId == 761))) {
            tvTemperature.setTextColor(Color.WHITE);
            tvDegree.setTextColor(Color.WHITE);
            tvWeather.setTextColor(Color.WHITE);
            tvDate.setTextColor(Color.WHITE);
            tvPlace.setTextColor(Color.WHITE);
            updatedString.setTextColor(Color.WHITE);
            tvTime.setTextColor(Color.WHITE);
        }

        //Checks if there is an activity matching the weather
        //to alert the user with a red circle near the floating action button.
        setAlert();

        //Sets the data into their corresponding TextViews to display.
        setWeatherText(weatherString);
        tvTemperature.setTempText(String.valueOf(temperatureLong), null);
        tvDate.setText(dateString);
        tvPlace.setText(placeString);
        tvTime.setText(" " + timeString);

        btnActivities.setOnClickListener(this);
        cvSunset.setOnClickListener(this);
        cvSunrise.setOnClickListener(this);
        ivSoundCloud.setOnClickListener(this);
    }

    /**
     * Set weather textview text.
     * @param weatherString current weather
     */
    private void setWeatherText(String weatherString) {
        if (weatherString.equals("Snow"))
            tvWeather.setText(R.string.snow);
        else if (weatherString.equals("Clouds"))
            tvWeather.setText(R.string.clouds);
        else if (weatherString.equals("Drizzle"))
            tvWeather.setText(R.string.drizzle);
        else if (weatherString.equals("Thunderstorm"))
            tvWeather.setText(R.string.thunderstorm);
        else if (weatherString.equals("Clear"))
            tvWeather.setText(R.string.clear);
        else if (weatherString.equals("Rain"))
            tvWeather.setText(R.string.rain);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Checks if there is an activity matching the weather
        //to alert the user with a red circle near the floating action button.
        setAlert();
    }

    /**
     * Create calendar and repeating alarm for notifications.
     */
    public void setNotification(){
        Log.v("setNotification", "in set NotificationReceiver");

        //Instance of an AlarmService
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        //Don't send notification it is past 7am
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if (currentHour <= 7)
        {
            //Create instance of a Calendar and set time to send notification.
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 7);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND, 0);

            //Intent that will send the application context to the notification broadcast receiver.
            Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
            intent.setAction("SEND_NOTIFICATION");

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //Create a repeating alarm that will call the notification
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }


    }

    /**
     * Inflates options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_option_menu, menu);

        return true;
    }

    //Creates options for the options menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //Calls changeTheme method from ThemeHelper class to change the theme.
            case R.id.options_light_theme:
                ThemeHelper.changeTheme(this, ThemeHelper.LIGHT);
                saveTheme(ThemeHelper.LIGHT);
                break;
            case R.id.options_dark_theme:
                ThemeHelper.changeTheme(this, ThemeHelper.DARK);
                saveTheme(ThemeHelper.DARK);
                break;
            case R.id.options_about:
                createAboutDialog(getString(R.string.about));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Inflates context menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    //Creates options for context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.context_weather:
                Uri uri = Uri.parse("https://www.theweathernetwork.com/ca");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.context_about:
                createAboutDialog("Description: " + weatherDesc + "\n" +
                                "Humid.: " + humidityString + "% \n" +
                                "Press.: " + pressureString + " hPa");
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        //Identifies which CardView has been clicked to open SunFragment based the CardView clicked (sunrise or sunset).
        String ResourceIdAsString = v.getResources().getResourceName(v.getId());
        Intent sunIntent = new Intent(this, SunActivity.class);
        sunIntent.putExtra("type", ResourceIdAsString);

        switch(id){
            case R.id.btn_open_activities:
                //Transfers tvWeather and tvTime to ActivitiesActivity in order to match the information to an activity's conditions.
                Intent openActIntent = new Intent(this, ActivitiesActivity.class);
                openActIntent.putExtra("tvWeather", weatherString);
                openActIntent.putExtra("tvTime", timeString);
                startActivity(openActIntent);
                break;

            case R.id.sunrise_card:
                sunIntent.putExtra("sunriseHour", sunriseTime);
                sunIntent.putExtra("place", placeString);
                startActivity(sunIntent);
                break;

            case R.id.sunset_card:
                sunIntent.putExtra("sunsetHour", sunsetTime);
                sunIntent.putExtra("place", placeString);
                startActivity(sunIntent);
                break;

            case R.id.ic_soundcloud:
                MediaPlayer mediaPlayer = new MediaPlayer();
                if ((weatherString.equals(getString(R.string.snow))))
                    mediaPlayer = MediaPlayer.create(this, R.raw.snow);
                else if (weatherString.equals(getString(R.string.clouds)))
                    mediaPlayer = MediaPlayer.create(this, R.raw.cloud);
                else if (weatherString.equals(getString((R.string.drizzle))))
                    mediaPlayer = MediaPlayer.create(this, R.raw.drizzle);
                else if (weatherString.equals(getString(R.string.thunderstorm)))
                    mediaPlayer = MediaPlayer.create(this, R.raw.thunderstorm);
                else if (weatherString.equals(getString((R.string.clear))))
                    mediaPlayer = MediaPlayer.create(this, R.raw.clear);
                else if (weatherString.equals(getString(R.string.rain)))
                    mediaPlayer = MediaPlayer.create(this, R.raw.rain);
                mediaPlayer.start();
                break;
        }
    }

    /**
     * Creates an alert dialog and takes the message paramater as the message to display in the alert dialog.
     * @param message
     */
    public void createAboutDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * Sets the background based on the current tvWeather.
     * @param weatherId
     */
    public void setStyle(int weatherId){
        if (weatherId >= 200 && weatherId <= 232)
            background.setBackgroundResource(R.drawable.thunderstorm);
        else if (weatherId >= 300 && weatherId <= 321)
            background.setBackgroundResource(R.drawable.drizzle);
        else if (weatherId >= 500 && weatherId <= 531)
            background.setBackgroundResource(R.drawable.rain);
        else if(weatherId >= 600 && weatherId <= 622)
            background.setBackgroundResource(R.drawable.snow);
        else if ((weatherId >= 701 && weatherId <= 741) || weatherId == 761)
            background.setBackgroundResource(R.drawable.mist);
        else if (weatherId == 751)
            background.setBackgroundResource(R.drawable.sand);
        else if (weatherId == 762)
            background.setBackgroundResource(R.drawable.ash);
        else if (weatherId == 771)
            background.setBackgroundResource(R.drawable.squall);
        else if (weatherId == 781)
            background.setBackgroundResource(R.drawable.tornado);
        else if (weatherId == 800)
            background.setBackgroundResource(R.drawable.clear);
        else
            background.setBackgroundResource(R.drawable.clouds);

    }

    /**
     * Calls OpenWeather API and parses the results with JSON Parser
     * from the AsyncTask background
     */
    private class APIAsyncTask extends AsyncTask<Void, Void, Weather>{
        @Override
        protected Weather doInBackground(Void... voids) {
            APIController apiController = new APIController();
            String result = apiController.getResults();
            JSONParser jsonParser = new JSONParser();
            weather = jsonParser.parse(result);
            return weather;
        }
    }

    /**
     * Uses AsyncTask to call OpenWeather data, retrieve data
     * and assigning the retrieved data to variables
     */
    private void setResults(){
        APIAsyncTask task = new APIAsyncTask();
        try {
            weather = task.execute().get();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            weatherId = weather.getId();
            temperatureLong = weather.getTemperature();
            weatherString = weather.getWeather();
            weatherDesc = weather.getWeatherDesc();
            dateString = Weather.getDate();
            placeString = weather.getCity() + ", " + weather.getCountry();
            timeString = Weather.getTime();
            sunriseTime = weather.getSunrise();
            sunsetTime = weather.getSunset();
            humidityString = weather.getHumidity();
            pressureString = weather.getPressure();

            //Saves the data retrieved to a database
            WeatherController weatherController = new WeatherController(this);
            weatherController.createWeather(weather);
        }
    }

    /**
     * Saves theme selection by using Shared Preferences
     */
    public void saveTheme(int theme){
        SharedPreferences sharedPreferences = getSharedPreferences("THEME", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("SELECTED_THEME", theme);
        editor.apply();
    }

    /**
     * Retrieves saved theme selection data and then loads the theme
     */
    public void loadTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences("THEME", MODE_PRIVATE);
        int savedTheme = sharedPreferences.getInt("SELECTED_THEME", ThemeHelper.LIGHT);
        ThemeHelper.currentTheme = savedTheme;
        ThemeHelper.setTheme(this);
    }

    /**
     * Checks whether there's an activity that matches with the weather,
     * if there is, a custom view (red circle) will be displayed
     * beside the floating action button to alert the user that there is an activity
     * matching with the weather.
     */
    protected void setAlert(){
        ActivityController activityController = new ActivityController(this);
        List<Activity> activityList = activityController.getAllActivities();
        for (int i = 0; i < activityList.size(); i++){
            if (activityList.get(i).getWeather().contains(weatherString)) {
                Log.d("test", String.valueOf(activityList.get(i).getWeather().contains(weatherString)));
                cvAlert.setVisibility(View.VISIBLE);
                return;
            }
        }

        cvAlert.setVisibility(View.GONE);
    }
}

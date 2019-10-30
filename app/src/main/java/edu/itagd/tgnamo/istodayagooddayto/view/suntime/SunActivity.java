package edu.itagd.tgnamo.istodayagooddayto.view.suntime;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.itagd.tgnamo.dataandstyle.ThemeHelper;
import edu.itagd.tgnamo.istodayagooddayto.R;

public class SunActivity extends AppCompatActivity {
    ConstraintLayout background;
    TextView header;

    Intent receivedIntent;
    String receivedMessage;
    String sunriseHour;
    String sunsetHour;
    String placeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Loads the theme before the application sets the views.
        ThemeHelper.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun);

        header = (TextView)findViewById(R.id.sun_header);
        background = (ConstraintLayout)findViewById(R.id.sun_background);
        receivedIntent = getIntent();
        receivedMessage = receivedIntent.getStringExtra("type");
        placeString = receivedIntent.getStringExtra("place");

        //Adds SunFragment dynamically.
        Fragment fragment = new SunFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //Sets the fragment with "sunrise information" if the Sunrise information has been clicked.
        if (receivedMessage.equals("edu.itagd.tgnamo.istodayagooddayto:id/sunrise_card"))
            {
            header.setText(R.string.tv_sunrise_card);
            background.setBackgroundResource(R.drawable.sunrise_bg);
            sunriseHour = receivedIntent.getStringExtra("sunriseHour");
            ((SunFragment) fragment).setSunTime(sunriseHour);
        }

        //Sets the fragment with "sunset information" if the Sunset information has been clicked.
        else {
            header.setText(R.string.tv_sunset_card);
            background.setBackgroundResource(R.drawable.sunset_bg);
            sunsetHour = receivedIntent.getStringExtra("sunsetHour");
            ((SunFragment) fragment).setSunTime(sunsetHour);
        }

        //Commits the fragment based on the selection of the user (sunrise or sunset)
        ((SunFragment) fragment).setPlace(placeString);
        transaction.add(R.id.fragment_container,fragment,"SunFragment");
        transaction.commit();
    }
}
package edu.itagd.tgnamo.istodayagooddayto.view.suntime;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.itagd.tgnamo.istodayagooddayto.R;
import edu.itagd.tgnamo.istodayagooddayto.model.Weather;

/**
 * A simple {@link Fragment} subclass.
 */
public class SunFragment extends Fragment {
    View rootView;
    TextView tvTime;
    TextView tvDate;
    TextView tvLocation;

    String timeString;
    String locationString;

    public SunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sunset_sunrise_fragment, container, false);
        tvTime = (TextView)rootView.findViewById(R.id.sun_time);
        tvDate = (TextView)rootView.findViewById(R.id.sun_date);
        tvLocation = (TextView) rootView.findViewById(R.id.sun_location);

        tvTime.setText(timeString);
        tvDate.setText(Weather.getDate());
        tvLocation.setText(locationString);

        return rootView;
    }

    /**
     * Method for the SunActivity (since it holds all the information) to pass the tvTime to this fragment
     * in order to make this fragment display the tvTime.
     * @param time
     */
    public void setSunTime(String time){
        this.timeString = time;
    }

    public void setPlace(String place){
        this.locationString = place;
    }
}

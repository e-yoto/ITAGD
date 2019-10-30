package edu.itagd.tgnamo.istodayagooddayto.view.activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import edu.itagd.tgnamo.istodayagooddayto.R;
import edu.itagd.tgnamo.istodayagooddayto.controller.database.activity.ActivityController;
import edu.itagd.tgnamo.istodayagooddayto.model.Activity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddActivityFragment extends DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    View rootView;
    EditText newActivity;
    TimePickerDialog timePickerDialog;
    TextView timeChosen;
    CheckBox checkSnow, checkDrizzle, checkThunderstorm, checkClouds, checkClear, checkRain;
    Button btnAddActivity, btnCancelActivity;
    Snackbar snackbar;

    private RecyclerView recyclerView;
    private ActivitiesAdapter adapter;
    private  RecyclerView.LayoutManager layoutManager;

    Activity activity;
    ActivityController activityController;
    Calendar calendar;
    int hour;
    int minute;
    Boolean isMorning;
    String weather = "";

    final private static int LINEAR_LAYOUT = 0;
    final private static int GRID_LAYOUT = 1;

    public AddActivityFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    /**
     * Create the Dialog and set its width and height.
     */
    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height= ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setLayout(width, height);
    }

    /**
     * Calls the methods that sets the fragment's views and loads information about the activity.
     * Sets the event listeners on the views.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_activity_layout, container);

        setFragmentViews();
        loadActivityData();

        checkSnow.setOnCheckedChangeListener(this);
        checkThunderstorm.setOnCheckedChangeListener(this);
        checkClear.setOnCheckedChangeListener(this);
        checkClouds.setOnCheckedChangeListener(this);
        checkDrizzle.setOnCheckedChangeListener(this);
        checkRain.setOnCheckedChangeListener(this);

        timeChosen.setOnClickListener(this);
        btnAddActivity.setOnClickListener(this);
        btnCancelActivity.setOnClickListener(this);

        this.activityController = new ActivityController(getActivity().getApplicationContext());

        return rootView;
    }

    /**
     * OnClick listener for entering information, saving, or cancelling.
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            //Time is selected, open the TimePickerDialog to enter tvTime.
            case R.id.edt_time:
                calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12)
                        {
                            isMorning = false;
                        }
                        else
                        {
                            isMorning = true;
                        }

                        if (hourOfDay == 0 && minute == 0){
                            timeChosen.setText("00:00");
                        }
                        else if (hourOfDay == 0){
                            timeChosen.setText("00:" + minute);
                        }
                        else if (minute < 10){
                            timeChosen.setText(hourOfDay + ":0" + minute );
                        }
                        else {
                            timeChosen.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, hour, minute, false);

                timePickerDialog.show();
                Log.d("editTime", "Success");
                Log.i("timeChosen", "" + timeChosen);
                break;

            //Save button clicked. If the Activity doesn't already exists, save information into a
            // new Activity object and call the method to save it to the database. If it exists,
            //update it in database.
            case R.id.btn_add_activity:
                try {
                    if (activity == null) {
                        Activity createdActivity = new Activity();

                        String occasionName = newActivity.getText().toString();
                        createdActivity.setActivity(occasionName);
                        String time = timeChosen.getText().toString();
                        createdActivity.setHour(time.substring(0, time.indexOf(":")));
                        createdActivity.setMinute(time.substring(time.lastIndexOf(":") + 1));
                        createdActivity.setWeather(weather);
                        createdActivity.setMorning(isMorning);

                        if (checkEntries(occasionName, time, weather)){
                            Toast.makeText(getContext(), "Enter valid entries", Toast.LENGTH_LONG).show();
                            break;
                        }

                        this.activityController.createActivity(createdActivity);
                        this.setLayoutManager(ActivitiesActivity.getLayoutManagerId());
                        this.dismiss();

                        //make snackbar that will allow user to undo activity creation
                        snackbar.make(v, "Activity created!", Snackbar.LENGTH_LONG)
                                .setAction("Undo", null).show();;
                        Log.i("createdActivity", createdActivity.toString());
                        Log.d("activitySaved", "Success");
                    }
                    else {
                        updateActivity();
                        this.setLayoutManager(ActivitiesActivity.getLayoutManagerId());
                        this.dismiss();
                        Log.i("updatedActivity", activity.toString());
                        Log.d("activityUpdated", "Success");
                    }
                }
                //Display toast to inform error.
                catch (Exception e) {
                    Toast toast = Toast.makeText(getActivity(), "Please enter all the required information", Toast.LENGTH_SHORT);
                    toast.show();
                    Log.e("createdActivity", "Failed");
                    Log.e("updatedActivity", "Failed");
                }
                break;

            //Cancel activity creation/update.
            case R.id.btn_cancel_activity:
                this.setLayoutManager(ActivitiesActivity.getLayoutManagerId());
                this.dismiss();
                Log.d("cancelActivity", "Success");
                break;

        }
    }

    /**
     * CheckChange listener for the checkboxes.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        Log.d("checkedId", String.valueOf(id));

        //If a button is checked, and the tvWeather string does not already contain the tvWeather, add
        //the tvWeather checked to the tvWeather string.
        if (buttonView.isChecked()) {
            switch (id) {
                case R.id.check_snow:
                    if (!weather.contains(getString(R.string.snow)))
                    {
                        weather += getString(R.string.snow) + " ";
                    }
                    break;

                case R.id.check_clear:
                    if (!weather.contains(getString(R.string.clear)))
                    {
                        weather += getString(R.string.clear) + " ";
                    }
                    break;

                case R.id.check_clouds:
                    if (!weather.contains(getString(R.string.clouds)))
                    {
                        weather += getString(R.string.clouds) + " ";
                    }
                    break;

                case R.id.check_drizzle:
                    if (!weather.contains(getString(R.string.drizzle)))
                    {
                        weather += getString(R.string.drizzle) + " ";
                    }
                    break;

                case R.id.check_rain:
                    if (!weather.contains(getString(R.string.rain)))
                    {
                        weather += getString(R.string.rain) + " ";
                    }
                    break;

                case R.id.check_thunderstorm:
                    if (!weather.contains(getString(R.string.thunderstorm)))
                    {
                        weather += getString(R.string.thunderstorm) + " ";
                    }
                    break;
            }
        }

        //If a checkbox is not checked but its tvWeather state is found in the string, remove it from
        //the tvWeather string.
        else {
            switch (id) {
                case R.id.check_snow:
                    if (weather.contains(getString(R.string.snow))) {
                        weather = weather.replace(getString(R.string.snow) + " ", "");
                    }
                    break;

                case R.id.check_clear:
                    if (weather.contains(getString(R.string.clear))) {
                        weather = weather.replace(getString(R.string.clear) + " ", "");
                    }
                    break;

                case R.id.check_clouds:
                    if (weather.contains(getString(R.string.clouds))) {
                        weather = weather.replace(getString(R.string.clouds) + " ", "");
                    }
                    break;

                case R.id.check_drizzle:
                    if (weather.contains(getString(R.string.drizzle))) {
                        weather = weather.replace(getString(R.string.drizzle) + " ", "");
                    }
                    break;

                case R.id.check_rain:
                    if (weather.contains(getString(R.string.rain))) {
                        weather = weather.replace(getString(R.string.rain) + " ", "");
                    }
                    break;

                case R.id.check_thunderstorm:
                    if (weather.contains(getString(R.string.thunderstorm))) {
                        weather = weather.replace(getString(R.string.thunderstorm) + " ", "");
                    }
                    break;
            }
        }
    }

    /**
     * Update data about an activity.
     */
    public void updateActivity() {
        Activity updatedActivity = new Activity();
        try{
            String occasionName = newActivity.getText().toString();
            String time = timeChosen.getText().toString();

            if (checkEntries(occasionName, time, weather)){
                Toast.makeText(getContext(), "Enter valid entries", Toast.LENGTH_LONG).show();
                return;
            }

            updatedActivity.setActivity(occasionName);
            updatedActivity.setHour(time.substring( 0, time.indexOf(":")));
            updatedActivity.setMinute(time.substring(time.lastIndexOf(":") + 1));
            updatedActivity.setWeather(weather);
            updatedActivity.setMorning(isMorning);
        }
        catch (Exception e){
            Log.e("updateInFragment", "Error in updateActivity()");
        }
        this.activityController.updateActivity(activity, updatedActivity);
    }

    /**
     * Remove the fragment.
     */
    public void setLayoutManager(int id) {
        this.recyclerView = getActivity().findViewById(R.id.activities_rv);
        switch(id){
            case LINEAR_LAYOUT:
                this.layoutManager = new LinearLayoutManager(getActivity());
                break;
            case GRID_LAYOUT:
                this.layoutManager = new GridLayoutManager(getActivity(), 2);
                break;
        }
        this.adapter = new ActivitiesAdapter(activityController.getAllActivities());
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Sets the add activity fragment's views.
     */
    private void setFragmentViews() {
        newActivity = (EditText) rootView.findViewById(R.id.edt_activity);
        timeChosen = (TextView)rootView.findViewById(R.id.edt_time);
        btnAddActivity = (Button) rootView.findViewById(R.id.btn_add_activity);
        btnCancelActivity = (Button) rootView.findViewById(R.id.btn_cancel_activity);
        checkSnow = (CheckBox) rootView.findViewById(R.id.check_snow);
        checkThunderstorm = (CheckBox) rootView.findViewById(R.id.check_thunderstorm);
        checkClear = (CheckBox) rootView.findViewById(R.id.check_clear);
        checkDrizzle = (CheckBox) rootView.findViewById(R.id.check_drizzle);
        checkClouds = (CheckBox) rootView.findViewById(R.id.check_clouds);
        checkRain = (CheckBox) rootView.findViewById(R.id.check_rain);
        checkSnow.setId(R.id.check_snow);
    }

    /**
     * Shows the current information of a selected activity if it has been already created.
     */
    private void loadActivityData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            activity = (Activity)bundle.getParcelable("ACTIVITY");
            Log.i("activityFromBundle", activity.toString() + activity.getActivity());
            if (activity != null) {
                this.newActivity.setText(activity.getActivity());
                this.timeChosen.setText(activity.getHour() + ":" + activity.getMinute());
                this.weather = activity.getWeather();
                if (weather.contains(getString(R.string.snow)))
                    checkSnow.setChecked(true);
                if (weather.contains(getString(R.string.clear)))
                    checkClear.setChecked(true);
                if (weather.contains(getString(R.string.clouds)))
                    checkClouds.setChecked(true);
                if (weather.contains(getString(R.string.drizzle)))
                    checkDrizzle.setChecked(true);
                if (weather.contains(getString(R.string.rain)))
                    checkRain.setChecked(true);
                if (weather.contains(getString(R.string.thunderstorm)))
                    checkThunderstorm.setChecked(true);
            }
        }
    }

    private boolean checkEntries(String occasion, String time, String weather){
        return occasion.isEmpty() || time.isEmpty() || weather.isEmpty();
    }
}


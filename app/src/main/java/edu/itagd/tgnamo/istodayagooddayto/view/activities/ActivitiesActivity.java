package edu.itagd.tgnamo.istodayagooddayto.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.itagd.tgnamo.dataandstyle.ThemeHelper;
import edu.itagd.tgnamo.istodayagooddayto.R;
import edu.itagd.tgnamo.istodayagooddayto.controller.database.activity.ActivityController;
import edu.itagd.tgnamo.istodayagooddayto.model.Activity;
import edu.itagd.tgnamo.istodayagooddayto.view.archives.ArchivesActivity;

public class ActivitiesActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<ActivitiesViewHolder> adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private ActivityController activityController;
    ImageView ivDeleteIcon;
    FloatingActionButton btnAddActivity;

    List<Activity> activities;
    private static String weather;
    private static String time;
    private static int layoutManagerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Loads the theme before the application sets the views.
        ThemeHelper.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_activity_layout);

        //Set RecyclerView
        setViews();

        //Inflates delete icon/button and action.
        LayoutInflater inflater = getLayoutInflater();
        CardView view = (CardView) inflater.inflate(R.layout.activities_row_layout, null);
        ivDeleteIcon = (ImageView) view.findViewById(R.id.ic_delete);

        //Gets data/conditions passed from the main activity.
        Intent conditions = getIntent();
        weather = conditions.getStringExtra("tvWeather");
        time = conditions.getStringExtra("tvTime");

        //Listeners for the delete icon and add button.
        ivDeleteIcon.setOnClickListener(this);
        btnAddActivity.setOnClickListener(this);

        Log.i("conditions", "Weather: " + weather + ", Time: " + time);
    }

    /**
     * Create options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activities_option_menu, menu);
        return true;
    }

    /**
     * Handles what happens when a menu item is clicked.
     * @param item item option clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.options_linear:
                this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                layoutManagerId = 0;
                break;
            case R.id.options_grid:
                this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                layoutManagerId = 1;
                break;
            case R.id.options_restore_archive:
                Intent archivesIntent = new Intent(this, ArchivesActivity.class);
                startActivityForResult(archivesIntent, 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Toast the name of the restored activity and update recycler view.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == android.app.Activity.RESULT_OK){
            String str = data.getStringExtra("ARCHIVE");

            setViews();

            Log.i("RestoredActivity", str);
            Toast.makeText(this, "Restored "+ str, Toast.LENGTH_SHORT).show();
        }

        else
            Toast.makeText(this, "No archive restored", Toast.LENGTH_SHORT).show();
    }

    public static int getLayoutManagerId(){
        return layoutManagerId;
    }
    /**
     * Returns the current tvWeather state.
     */
    public static String getWeatherFromIntent() {
        Log.v("weatherFromIntent", weather);
        return weather;
    }

    /**
     * OnClick listener for the button that opens the Add Activity Fragment
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btn_add:
                AddActivityFragment addActivityFragment = new AddActivityFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                addActivityFragment.show(fragmentManager, "Showing AddActivityFragment");
                Log.d("addActivity", "Success");
                break;
        }
    }

    public void setViews(){
        //Get all activities from database.
        activities = new ArrayList<>();
        activityController = new ActivityController(this);
        this.activities = this.activityController.getAllActivities();

        //Set all activities in RecyclerView.
        this.recyclerView = findViewById(R.id.activities_rv);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new ActivitiesAdapter(activities);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);

        btnAddActivity = (FloatingActionButton) findViewById(R.id.btn_add);

    }
}

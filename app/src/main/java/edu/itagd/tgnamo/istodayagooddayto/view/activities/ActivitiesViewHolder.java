package edu.itagd.tgnamo.istodayagooddayto.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.itagd.tgnamo.istodayagooddayto.R;
import edu.itagd.tgnamo.istodayagooddayto.controller.database.activity.ActivityController;
import edu.itagd.tgnamo.istodayagooddayto.controller.database.activity.ArchivesController;
import edu.itagd.tgnamo.istodayagooddayto.model.Activity;
import edu.itagd.tgnamo.istodayagooddayto.view.custom.CustomTextView;

public class ActivitiesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    private CustomTextView txtActivityName;
    private TextView txtActivityTime;
    private TextView txtActivityWeather;
    private Activity activity;
    private Activity deletedActivity;
    private CardView cardView;
    private ActivityController activityController;
    private List<Activity> activities;
    private ActivitiesAdapter activitiesAdapter;
    Resources resources;
    ImageView ivDelete;
    int position;
    Snackbar snackbar;

    public ActivitiesViewHolder(@NonNull View itemView, ActivitiesAdapter activitiesAdapter, List<Activity> activities) {
        super(itemView);

        this.activitiesAdapter = activitiesAdapter;

        //Get all Activities.
        this.activities = activities;

        //Get views.
        txtActivityName = itemView.findViewById(R.id.txt_activity_name);
        txtActivityTime = itemView.findViewById(R.id.txt_time);
        txtActivityWeather = itemView.findViewById(R.id.txt_weather_state);
        ivDelete = itemView.findViewById(R.id.ic_delete);
        cardView = itemView.findViewById(R.id.cardViewActivity);
        resources = itemView.getContext().getResources();

        //Set event listeners.
        ivDelete.setOnClickListener(this);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    /**
     * Set views in the recycler view.
     * @param activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
        this.txtActivityName.setHeaderText(activity.getActivity(), null);
        this.txtActivityTime.setText(activity.getHour()+":"+activity.getMinute());
        this.txtActivityWeather.setText(activity.getWeather());
        String weather = ActivitiesActivity.getWeatherFromIntent();
        if (this.activity.getWeather().contains(weather)){
            this.cardView.setCardBackgroundColor(resources.getColor(R.color.cvGreen));
        }
    }

    /**
     * OnClick listener for deleting.
     * @param v
     */
    @Override
    public void onClick(final View v) {
        int id = v.getId();
        Context context = v.getContext();
        activityController = new ActivityController(context);
        position = this.getAdapterPosition();
        switch (id){
            case R.id.ic_delete:
                Log.i("deleteIconClicked", "delete button clicked");
                Log.i("deleteIconClicked", activity.toString());
                new AlertDialog.Builder(context)
                        .setTitle(R.string.delete_activity)
                        .setMessage(R.string.delete_message)
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //save deleted activity in case user wants to undo
                                deletedActivity = activities.get(position);
                                activities.remove(position);
                                activityController.deleteActivity(activity);
                                activitiesAdapter.notifyItemRemoved(position);
                                activitiesAdapter.notifyItemRangeChanged(position, activities.size());
                                activitiesAdapter.notifyDataSetChanged();
                                snackbar.make(v, R.string.activity_deleted, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.archive, new ArchiveListener()).show();
                            }
                        }).create().show();
                break;

            default:
                //Displays activity's position
                //Toast.makeText(context, "Activity: " + activity.getActivity() + ", position: " + position, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * OnLongClick listener for updating an activity.
     * @param v
     */
    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        Context context = v.getContext();
        activityController = new ActivityController(context);
        position = this.getAdapterPosition();
        if (id == R.id.ic_delete) {
            return false;
        }
        else
        {
            Log.v("longPressedRV", "long pressed worked");
            AddActivityFragment saveFragment = new AddActivityFragment();
            Bundle args = new Bundle();
            args.putParcelable("ACTIVITY", activity);
            saveFragment.setArguments(args);

            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            saveFragment.show(fragmentManager, "Showing AddActivityFragment");

            return true;
        }
    }

    /**
     * Add deleted activity to archive
     */
    public class ArchiveListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            ArchivesController archivesController = new ArchivesController(v.getContext());
            archivesController.createArchive(deletedActivity);
        }
    }
}
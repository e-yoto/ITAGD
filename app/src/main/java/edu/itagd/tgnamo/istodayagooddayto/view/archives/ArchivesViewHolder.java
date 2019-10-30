package edu.itagd.tgnamo.istodayagooddayto.view.archives;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import edu.itagd.tgnamo.istodayagooddayto.view.activities.ActivitiesActivity;

public class ArchivesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtActivityName;
    private TextView txtActivityTime;
    private TextView txtActivityWeather;
    private Activity activity;
    private Activity restoredActivity;
    private CardView cardView;
    private List<Activity> archives;
    private ActivityController activityController;
    private ArchivesController archivesController;
    private ArchivesAdapter archivesAdapter;
    private View itemView;
    Resources resources;
    ImageView ivRestore;
    int position;
    Snackbar snackbar;

    public ArchivesViewHolder(@NonNull View itemView, ArchivesAdapter archivesAdapter, List<Activity> archives) {
        super(itemView);

        //So that we can get context from the originating itemView
        this.itemView = itemView;

        this.archivesAdapter = archivesAdapter;

        //Get all Activities.
        this.archives = archives;

        //Get views.
        txtActivityName = itemView.findViewById(R.id.txt_activity_name);
        txtActivityTime = itemView.findViewById(R.id.txt_time);
        txtActivityWeather = itemView.findViewById(R.id.txt_weather_state);
        ivRestore = itemView.findViewById(R.id.ic_restore);
        cardView = itemView.findViewById(R.id.cardViewActivity);
        resources = itemView.getContext().getResources();

        //Set event listeners.
        ivRestore.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    /**
     * Set views in the recycler view.
     * @param activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
        this.txtActivityName.setText(activity.getActivity());
        this.txtActivityTime.setText(activity.getHour()+":"+activity.getMinute());
        this.txtActivityWeather.setText(activity.getWeather());
    }


    @Override
    public void onClick(final View v) {
        int id = v.getId();
        Log.v("clickedOnRestore", "restore icon clicked");
        Context context = v.getContext();
        archivesController = new ArchivesController(context);
        activityController = new ActivityController(context);
        position = this.getAdapterPosition();
        switch (id){
            case R.id.ic_restore:
                //Delete activity from archive database, and add into activities database.
                restoredActivity = archives.get(position);
                Log.v("restoredActivityInfo", restoredActivity.toString());
                archives.remove(position);
                archivesController.deleteArchive(activity);
                activityController.createActivity(activity);
                archivesAdapter.notifyItemRemoved(position);
                archivesAdapter.notifyItemRangeChanged(position, archives.size());
                archivesAdapter.notifyDataSetChanged();
                snackbar.make(v, R.string.snackbar_activity_restored, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ok", new ArchiveListener()).show();
                break;

        }
    }

    /**
     * Listener for when the Snackbar action is clicked
     */
    public class ArchiveListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //Create intent to return to Activities Activity and put extra information into intent.
            Context context = itemView.getContext();
            Intent returnToActivities = new Intent(v.getContext(), ActivitiesActivity.class);
            String activityName = activity.getActivity();
            returnToActivities.putExtra("ARCHIVE", activityName);
            returnToActivities.putExtra("ARCHIVED_ACT",restoredActivity);

            //Set Result to OK and finish activity.
            ((ArchivesActivity)context).setResult(ArchivesActivity.RESULT_OK, returnToActivities);
            ((ArchivesActivity)context).finish();
        }
    }
}

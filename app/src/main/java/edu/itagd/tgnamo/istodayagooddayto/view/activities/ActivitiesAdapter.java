package edu.itagd.tgnamo.istodayagooddayto.view.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import edu.itagd.tgnamo.istodayagooddayto.R;
import edu.itagd.tgnamo.istodayagooddayto.model.Activity;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesViewHolder> {
    private List<Activity> activities;

    public ActivitiesAdapter(List<Activity> activities) {
        this.activities = activities;
    }

    @NonNull
    @Override
    public ActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        //Inflate the layout
        View view = LayoutInflater.from(context).inflate(R.layout.activities_row_layout, viewGroup, false);

        // Create a ViewHolder.
        ActivitiesViewHolder viewHolder = new ActivitiesViewHolder(view, this, activities);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesViewHolder activitiesViewHolder, int i) {
        //Get Activity at index i
        Activity activity = activities.get(i);
        activitiesViewHolder.setActivity(activity);
    }

    /**
     * Returns amount of activities in the list.
     */
    @Override
    public int getItemCount() {
        return activities.size();
    }

    /**
     * Returns all activities in a List.
     * @return
     */
    public List<Activity> getActivities() {
        return  activities;
    }
}


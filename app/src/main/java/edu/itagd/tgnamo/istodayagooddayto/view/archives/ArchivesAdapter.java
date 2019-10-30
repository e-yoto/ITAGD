package edu.itagd.tgnamo.istodayagooddayto.view.archives;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.itagd.tgnamo.istodayagooddayto.R;
import edu.itagd.tgnamo.istodayagooddayto.model.Activity;

public class ArchivesAdapter extends RecyclerView.Adapter<ArchivesViewHolder> {
    private List<Activity> activities;

    public ArchivesAdapter(List<Activity> activities) {
        this.activities = activities;
    }

    @NonNull
    @Override
    public ArchivesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();

        //Inflate the layout
        View view = LayoutInflater.from(context).inflate(R.layout.archive_row_layout, viewGroup, false);

        // Create a ViewHolder.
        ArchivesViewHolder viewHolder = new ArchivesViewHolder(view, this, activities);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArchivesViewHolder archivesViewHolder, int i) {
        //Get Activity at index i
        final Activity activity = activities.get(i);
        archivesViewHolder.setActivity(activity);

    }

    @Override
    public int getItemCount() {
        return activities.size();
    }
}

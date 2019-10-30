package edu.itagd.tgnamo.istodayagooddayto.view.archives;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;

import java.util.List;

import edu.itagd.tgnamo.dataandstyle.ThemeHelper;
import edu.itagd.tgnamo.istodayagooddayto.R;
import edu.itagd.tgnamo.istodayagooddayto.controller.database.activity.ArchivesController;
import edu.itagd.tgnamo.istodayagooddayto.model.Activity;

public class ArchivesActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<ArchivesViewHolder> adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private ArchivesController archivesController;
    ImageView ivRestoreIcon;

    List<Activity> archives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Loads the theme before the application sets the views.
        ThemeHelper.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archives_layout);

        //Get all activities from database.
        archivesController = new ArchivesController(this);
        this.archives = this.archivesController.getAllArchives();

        //Set all activities in RecyclerView.
        this.recyclerView = findViewById(R.id.rv_archives);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new ArchivesAdapter(archives);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);

        //Inflates delete icon/button and action.
        LayoutInflater inflater = getLayoutInflater();
        CardView view = (CardView) inflater.inflate(R.layout.archive_row_layout, null);
        ivRestoreIcon = (ImageView) view.findViewById(R.id.ic_delete);
    }
}

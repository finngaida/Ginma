package de.ricoklimpel.ginma;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Arrays;

public class ChooseProjektActivity extends AppCompatActivity {

    RecyclerView RV_projects;
    RecyclerView.Adapter RVA_projects;
    RecyclerView.LayoutManager RVLM_projects;

    FloatingActionButton FAB_Help;
    FloatingActionButton FAB_Plus;
    static FloatingActionsMenu FAM_Projects;

    static ArrayList<String> ArrayProjectObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_projekt);


        FAM_Projects = (FloatingActionsMenu)findViewById(R.id.FAM_projects);
        FAM_Projects.setVisibility(View.GONE);

        ArrayProjectObjects = new ArrayList<>();

        ArrayProjectObjects.addAll(Arrays.asList("hallo", "hallo2", "hallo3", "hallo2"
                , "hallo3", "hallo2", "hallo3", "hallo2", "hallo3", "hallo2", "hallo3"
                , "hallo2", "hallo3", "hallo2", "hallo3"));

        RV_projects = (RecyclerView)findViewById(R.id.RV_projects);
        RVLM_projects = new LinearLayoutManager(this);

        RV_projects.setLayoutManager(RVLM_projects);

        RVA_projects = new RVA_projectsAdapterClass();
        RV_projects.setAdapter(RVA_projects);


    }


}

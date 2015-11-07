package de.ricoklimpel.ginma;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.rengwuxian.materialedittext.MaterialEditText;

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


    ButtonFlat btn_AD_addproject;
    MaterialEditText eT_AD_addproject;


    SharedPreferences prefs;
    SharedPreferences.Editor prefseditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_projekt);


        FAM_Projects = (FloatingActionsMenu)findViewById(R.id.FAM_projects);

        ArrayProjectObjects = new ArrayList<>();

        ArrayProjectObjects.addAll(Arrays.asList("hallo", "hallo2", "hallo3", "hallo2"
                , "hallo3", "hallo2", "hallo3", "hallo2", "hallo3", "hallo2", "hallo3"
                , "hallo2", "hallo3", "hallo2", "hallo3"));

        RV_projects = (RecyclerView)findViewById(R.id.RV_projects);
        RVLM_projects = new LinearLayoutManager(this);

        RV_projects.setLayoutManager(RVLM_projects);

        RVA_projects = new RVA_projectsAdapterClass();
        RV_projects.setAdapter(RVA_projects);



        FAB_Help = (FloatingActionButton)findViewById(R.id.FAB_help);
        FAB_Plus = (FloatingActionButton)findViewById(R.id.FAB_plus);
        FAB_Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddProject_AlertDialog();

            }
        });


    }


    private void AddProject_AlertDialog() {
        FAM_Projects.collapse();

        LayoutInflater AD_inflater = getLayoutInflater();
        View AD_View = AD_inflater.inflate(R.layout.ad_textinput_projects,null);

        final AlertDialog.Builder AD_addProjectbuilder = new AlertDialog.Builder(ChooseProjektActivity.this);
        //AD_addProjectbuilder.setIcon(R.drawable.ic_add_black_24dp);
        AD_addProjectbuilder.setTitle("Neues Projekt hinzufügen");
        AD_addProjectbuilder.setView(AD_View);

        btn_AD_addproject = (ButtonFlat)AD_View.findViewById(R.id.btn_AD_addprojects);
        eT_AD_addproject = (MaterialEditText)AD_View.findViewById(R.id.eT_AD_addprojects);

        final AlertDialog AD_addProject = AD_addProjectbuilder.create();

        btn_AD_addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eT_AD_addproject.getText().toString() != ""){
                    if (eT_AD_addproject.length()>=15){

                        //Flash Button red or something?

                    }else{

                        ArrayProjectObjects.add(eT_AD_addproject.getText().toString());
                        AD_addProject.dismiss();
                    }

                }

            }
        });

        AD_addProject.show();
    }


}

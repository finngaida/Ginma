package de.ricoklimpel.ginma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;


public class ChooseProjektActivity extends AppCompatActivity {

    static Activity CPA_activityContent;

    //Sagt an in welchen "Verzeichnis" sich der Nutzer gerade aufhält
    static String USERS_PATH;

    RecyclerView RV_projects;
    static RecyclerView.Adapter RVA_projects;
    RecyclerView.LayoutManager RVLM_projects;


    FloatingActionButton FAB_Help;
    FloatingActionButton FAB_Plus;
    static FloatingActionsMenu FAM_Projects;

    static ArrayList<String> ArrayProjectObjects;

    ButtonFlat btn_AD_addproject;
    MaterialEditText eT_AD_addproject;

    SharedPreferences prefs;
    SharedPreferences.Editor prefseditor;

    Toolbar toolbar_chooseproject;
    ActionBar actionbar_chooseproject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_projekt);
        CPA_activityContent = this;



        toolbar_chooseproject = (Toolbar)findViewById(R.id.Toolbar_ChooseProject);
        setSupportActionBar(toolbar_chooseproject);
        toolbar_chooseproject.setTitle("Projekte");
        if(Build.VERSION.SDK_INT>=21){
            toolbar_chooseproject.setElevation(25);
        }
        //actionbar_chooseproject = getSupportActionBar();
        //actionbar_chooseproject.setDisplayHomeAsUpEnabled(true);


        FAM_Projects = (FloatingActionsMenu)findViewById(R.id.FAM_projects);
        FAB_Help = (FloatingActionButton)findViewById(R.id.FAB_help);
        FAB_Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseProjektActivity.this, HelpActvity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

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

                if (eT_AD_addproject.getText().toString() != "") {
                    if (eT_AD_addproject.length() > 20) {

                        //Flash Button red or something?

                    } else {

                        ArrayProjectObjects.add(eT_AD_addproject.getText().toString());
                        RVA_projects.notifyDataSetChanged();
                        RV_projects.smoothScrollToPosition(ArrayProjectObjects.size());

                        AD_addProject.dismiss();
                    }

                }

            }
        });

        AD_addProject.show();
    }



    @Override
    protected void onPause() {
        super.onPause();

        prefs = this.getSharedPreferences("ginma", MODE_PRIVATE);
        prefseditor = prefs.edit();

        prefseditor.putString("projects", convertToString(ArrayProjectObjects));
        prefseditor.commit();

    }


    @Override
    protected void onResume() {
        super.onResume();

        ArrayProjectObjects = new ArrayList<>();


        prefs = this.getSharedPreferences("ginma", MODE_PRIVATE);
        String stringback =  prefs.getString("projects","");

        ArrayProjectObjects.addAll(Arrays.asList(stringback.split(","))) ;

        RV_projects = (RecyclerView)findViewById(R.id.RV_projects);
        RVLM_projects = new LinearLayoutManager(this);

        RV_projects.setLayoutManager(RVLM_projects);

        RVA_projects = new RVA_projectsAdapterClass();
        RV_projects.setAdapter(RVA_projects);
    }


    static public void DeleteDialog(final int i, Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle("Projekt löschen")
                .setMessage("Bist du dir sicher das '" +
                        ChooseProjektActivity.ArrayProjectObjects.get(i) +
                        "' und sämtliche Daten in diesem Projekt gelöscht werden soll?")

                .setPositiveButton("löschen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        ChooseProjektActivity.ArrayProjectObjects.remove(i);
                        RVA_projects.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_warning_black_36dp)
                .show();
    }


    private String convertToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list)
        {
            sb.append(delim);
            sb.append(s);;
            delim = ",";
        }
        return sb.toString();
    }


    private ArrayList<String> convertToArray(String string) {

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
        return list;
    }


    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_choose_projekt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if(id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}


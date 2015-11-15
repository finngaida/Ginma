package de.ricoklimpel.ginma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;


public class ChooseProjektActivity extends AppCompatActivity {

    static Activity CPA_activityContent;

    //Sagt an in welchen "Verzeichnis" sich der Nutzer gerade aufhält
    static String Projekt_ID;


    RecyclerView RV_projects;
    static RecyclerView.Adapter RVA_projects;
    RecyclerView.LayoutManager RVLM_projects;


    //Button zum aufrufen der Hilfe und hinzufügen eiens Neuen Projektes
    FloatingActionButton FAB_Help;
    FloatingActionButton FAB_Plus;
    static FloatingActionsMenu FAM_Projects;


    //Liste der Projektnamen, die in die Card Views geladen werden
    static ArrayList<String> ArrayProjectNames;

    String Projekt_quantity;


    //Bedienelemente im "Neues Projekt" Dialog
    Button btn_AD_addproject;
    MaterialEditText eT_AD_addproject;


    //Shared Prefernces Manager zum Speichern und abrufen von Daten
    SharedPreferences prefs_values;
    SharedPreferences.Editor prefseditor_values;


    //Shared Preferences Manager zum erstellen von neuen Kategorien
    static SharedPreferences prefs_create;
    static SharedPreferences.Editor prefseditor_create;

    Toolbar toolbar_chooseproject;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_projekt);
        CPA_activityContent = this;



        prefs_values = this.getSharedPreferences("values", MODE_PRIVATE);
        String first_start =  prefs_values.getString("first_start","");

        if(first_start == ""){

            prefs_values = this.getSharedPreferences("values", MODE_PRIVATE);
            prefseditor_values = prefs_values.edit();

            prefseditor_values.putString("first_start", "false");
            prefseditor_values.commit();

        }else{

        }


        Init_Toolbar();

        Init_FabMenu();


    }

    private void Init_Toolbar() {
        toolbar_chooseproject = (Toolbar)findViewById(R.id.Toolbar_ChooseProject);
        setSupportActionBar(toolbar_chooseproject);
        toolbar_chooseproject.setTitle("Projekte");
        if(Build.VERSION.SDK_INT>=21){
            toolbar_chooseproject.setElevation(25);
        }
    }


    private void Init_FabMenu() {
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
        AD_addProjectbuilder.setTitle("Neues Projekt hinzufügen");
        AD_addProjectbuilder.setView(AD_View);

        btn_AD_addproject = (Button)AD_View.findViewById(R.id.btn_AD_addprojects);
        eT_AD_addproject = (MaterialEditText)AD_View.findViewById(R.id.eT_AD_addprojects);

        final AlertDialog AD_addProject = AD_addProjectbuilder.create();

        btn_AD_addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eT_AD_addproject.getText().toString().isEmpty()) {

                    Toast.makeText(ChooseProjektActivity.this, "Bitte trage einen Projektnamen ein!", Toast.LENGTH_SHORT).show();

                }else if (eT_AD_addproject.getText().toString().length() > 20){

                    Toast.makeText(ChooseProjektActivity.this, "Nicht mehr als 20 Zeichen!", Toast.LENGTH_SHORT).show();
                    
                }else{

                    addProjekt();


                    RVA_projects.notifyDataSetChanged();
                    RV_projects.smoothScrollToPosition(ArrayProjectNames.size());

                    AD_addProject.dismiss();
                }

            }
        });

        AD_addProject.show();
    }



    private void addProjekt() {

        ArrayProjectNames.add(eT_AD_addproject.getText().toString());

        prefs_values = this.getSharedPreferences("values", MODE_PRIVATE);
        prefseditor_values = prefs_values.edit();

        prefseditor_values.putString("project_names", convertToString(ArrayProjectNames));
        prefseditor_values.commit();





        prefs_create = this.getSharedPreferences("ID_" + String.valueOf(ArrayProjectNames.size()-1), MODE_PRIVATE);
        prefseditor_create = prefs_create.edit();

        prefseditor_create.putString("category_names", "");
        prefseditor_create.commit();


    }


    @Override
    protected void onPause() {
        super.onPause();

        prefs_values = this.getSharedPreferences("values", MODE_PRIVATE);
        prefseditor_values = prefs_values.edit();

        prefseditor_values.putString("project_names", convertToString(ArrayProjectNames));
        prefseditor_values.putInt("project_quantity", ArrayProjectNames.size());
        prefseditor_values.commit();

    }


    @Override
    protected void onResume() {
        super.onResume();

        loadProjektnameArray();

        Init_RecyclerView();
    }


    private void Init_RecyclerView() {
        RV_projects = (RecyclerView)findViewById(R.id.RV_projects);
        RVLM_projects = new LinearLayoutManager(this);

        RV_projects.setLayoutManager(RVLM_projects);

        RVA_projects = new RVA_projectsAdapterClass();
        RV_projects.setAdapter(RVA_projects);
    }


    private void loadProjektnameArray() {
        ArrayProjectNames = new ArrayList<>();

        prefs_values = this.getSharedPreferences("values", MODE_PRIVATE);
        String stringback =  prefs_values.getString("project_names","");

        if(stringback!=""){
            ArrayProjectNames.addAll(Arrays.asList(stringback.split(","))) ;
        }

    }


    static public void DeleteDialog(final int i, Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle("Projekt löschen")
                .setMessage("Bist du dir sicher das '" +
                        ChooseProjektActivity.ArrayProjectNames.get(i) +
                        "' und sämtliche Daten in diesem Projekt gelöscht werden soll?")

                .setPositiveButton("löschen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        prefs_create = CPA_activityContent.getSharedPreferences(
                                "ID_" + String.valueOf(i), MODE_PRIVATE);
                        prefs_create.edit().clear().commit();


                        ChooseProjektActivity.ArrayProjectNames.remove(i);
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


    public static  String convertToString(ArrayList<String> list) {

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

        onPause();

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

        //Zurückbutton in der Toolbaa
        if(id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public static void open_Popup(View v,final int i, final Activity activity) {


        PopupMenu popup = new PopupMenu(ChooseProjektActivity.CPA_activityContent,v);
        popup.getMenuInflater().inflate(R.menu.popup_rename_delete,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuitem_rename: {


                        break;
                    }
                    case R.id.menuitem_delete: {

                        DeleteDialog(i,activity);

                        break;
                    }
                }

                return false;
            }
        });

        popup.show();


    }
}

